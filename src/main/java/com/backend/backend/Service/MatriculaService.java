package com.backend.backend.Service;

import com.backend.backend.DTO.MatriculaDTO.MatriculaCreateDTO;
import com.backend.backend.DTO.MatriculaDTO.MatriculaDetalleCreateDTO;
import com.backend.backend.DTO.MatriculaDTO.MatriculaDetalleResponseDTO;
import com.backend.backend.DTO.MatriculaDTO.MatriculaResponseDTO;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.EstadoMatriculaDetalle;
import com.backend.backend.Entity.EstadoPago;
import com.backend.backend.Entity.Horario;
import com.backend.backend.Entity.Matricula;
import com.backend.backend.Entity.MatriculaDetalle;
import com.backend.backend.Entity.Pago;
import com.backend.backend.Entity.TurnoHorario;
import com.backend.backend.Entity.TurnoMatriculaDetalle;
import com.backend.backend.Entity.Usuario;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.DeporteRepository;
import com.backend.backend.Repository.HorarioRepository;
import com.backend.backend.Repository.MatriculaDetalleRepository;
import com.backend.backend.Repository.MatriculaRepository;
import com.backend.backend.Repository.PagoRepository;
import com.backend.backend.Repository.PrecioMatriculaRepository;
import com.backend.backend.Repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class MatriculaService {

    private static final int TOTAL_SESIONES = 12;
    private static final BigDecimal PRECIO_DEFAULT_DOCE_SESIONES = new BigDecimal("120.00");
    private static final String ROL_CLIENTE = "CLIENTE";

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DeporteRepository deporteRepository;
    private final HorarioRepository horarioRepository;
    private final MatriculaDetalleRepository matriculaDetalleRepository;
    private final PagoRepository pagoRepository;
    private final PrecioMatriculaRepository precioMatriculaRepository;
    private final ModelMapper modelMapper;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            UsuarioRepository usuarioRepository,
                            DeporteRepository deporteRepository,
                            HorarioRepository horarioRepository,
                            MatriculaDetalleRepository matriculaDetalleRepository,
                            PagoRepository pagoRepository,
                            PrecioMatriculaRepository precioMatriculaRepository,
                            ModelMapper modelMapper) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
        this.deporteRepository = deporteRepository;
        this.horarioRepository = horarioRepository;
        this.matriculaDetalleRepository = matriculaDetalleRepository;
        this.pagoRepository = pagoRepository;
        this.precioMatriculaRepository = precioMatriculaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public MatriculaResponseDTO crear(MatriculaCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));

        if (!tieneRolCliente(usuario)) {
            throw new IllegalArgumentException("Solo los usuarios con rol CLIENTE pueden matricularse");
        }

        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setFechaMatricula(dto.getFechaMatricula() != null ? dto.getFechaMatricula() : LocalDate.now());
        matricula.setTotalSesiones(contarDeportesUnicos(dto.getDetalles()) * TOTAL_SESIONES);
        matricula.setSesionesConsumidas(0);
        matricula.setObservaciones(dto.getObservaciones());

        validarDetallesDuplicados(dto.getDetalles());
        List<MatriculaDetalle> detalles = dto.getDetalles()
                .stream()
                .map(this::crearDetalle)
                .toList();

        validarReglasDeHorarios(detalles);
        validarDeportesActivosDelCliente(usuario, detalles);
        validarCuposDisponibles(detalles);
        detalles.forEach(detalle -> {
            detalle.setTotalSesiones(TOTAL_SESIONES);
            detalle.setSesionesConsumidas(0);
            matricula.addDetalle(detalle);
        });

        Matricula guardada = matriculaRepository.save(matricula);
        crearPagoAutomatico(guardada);

        return toResponseDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarTodos() {
        return matriculaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPorCliente(UUID usuarioId) {
        return matriculaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private MatriculaDetalle crearDetalle(MatriculaDetalleCreateDTO dto) {
        Deporte deporte = deporteRepository.findById(dto.getDeporteId())
                .orElseThrow(() -> new ResourceNotFoundException("Deporte no encontrado con ID: " + dto.getDeporteId()));

        Horario horario = horarioRepository.findById(dto.getHorarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con ID: " + dto.getHorarioId()));

        if (horario.getCampo() == null || horario.getCampo().getDeporte() == null) {
            throw new IllegalArgumentException("El horario no tiene un campo/deporte asociado");
        }
        if (!horario.getCampo().getDeporte().getId().equals(deporte.getId())) {
            throw new IllegalArgumentException("El horario seleccionado no pertenece al campo del deporte indicado");
        }
        if (dto.getDiaSemana() != horario.getDiaSemana()) {
            throw new IllegalArgumentException("El dia elegido no coincide con el dia del horario seleccionado");
        }
        TurnoHorario turno = normalizarTurno(dto.getTurno());
        if (turno != horario.getTurno()) {
            throw new IllegalArgumentException("El turno elegido no coincide con el horario seleccionado");
        }

        LocalDate fechaInicio = dto.getFechaInicio() != null ? dto.getFechaInicio() : LocalDate.now();
        if (dto.getFechaFin() != null && dto.getFechaFin().isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha fin no puede ser menor que la fecha inicio");
        }

        MatriculaDetalle detalle = new MatriculaDetalle();
        detalle.setDeporte(deporte);
        detalle.setHorario(horario);
        detalle.setDiaSemana(horario.getDiaSemana());
        detalle.setTurno(toTurnoMatriculaDetalle(turno));
        detalle.setFechaInicio(fechaInicio);
        detalle.setFechaFin(dto.getFechaFin());
        return detalle;
    }

    private boolean tieneRolCliente(Usuario usuario) {
        return usuario.getRoles()
                .stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase(ROL_CLIENTE));
    }

    private void validarDetallesDuplicados(List<MatriculaDetalleCreateDTO> detalles) {
        Set<String> claves = new HashSet<>();
        for (MatriculaDetalleCreateDTO detalle : detalles) {
            String clave = detalle.getDeporteId() + "-" + detalle.getHorarioId() + "-" + detalle.getDiaSemana() + "-" + detalle.getTurno();
            if (!claves.add(clave)) {
                throw new IllegalArgumentException("No se puede repetir el mismo deporte, horario, dia y turno en una matricula");
            }
        }
    }

    private void validarReglasDeHorarios(List<MatriculaDetalle> detalles) {
        Set<String> deportePorDia = new HashSet<>();
        Map<UUID, Set<String>> diasPorDeporte = new HashMap<>();

        for (int i = 0; i < detalles.size(); i++) {
            MatriculaDetalle actual = detalles.get(i);
            Horario horarioActual = actual.getHorario();

            validarDiaPermitido(actual);
            diasPorDeporte
                    .computeIfAbsent(actual.getDeporte().getId(), id -> new HashSet<>())
                    .add(actual.getDiaSemana().name());

            String deporteDia = actual.getDeporte().getId() + "-" + actual.getDiaSemana();
            if (!deportePorDia.add(deporteDia)) {
                throw new IllegalArgumentException("Solo se permite una sesion por dia para el mismo deporte");
            }

            for (int j = i + 1; j < detalles.size(); j++) {
                MatriculaDetalle siguiente = detalles.get(j);
                Horario horarioSiguiente = siguiente.getHorario();

                if (actual.getDiaSemana().equals(siguiente.getDiaSemana())
                        && horariosSeCruzan(horarioActual, horarioSiguiente)) {
                    throw new IllegalArgumentException("No se puede matricular en horarios que se cruzan el mismo dia");
                }
            }
        }

        for (Set<String> dias : diasPorDeporte.values()) {
            if (dias.size() != 3) {
                throw new IllegalArgumentException("Cada deporte debe tener exactamente 3 dias de asistencia por semana");
            }
        }
    }

    private void validarDiaPermitido(MatriculaDetalle detalle) {
        if (detalle.getDiaSemana() == null) {
            throw new IllegalArgumentException("El dia elegido debe estar entre lunes y sabado");
        }
    }

    private boolean horariosSeCruzan(Horario primero, Horario segundo) {
        LocalTime inicioPrimero = primero.getHoraInicio();
        LocalTime finPrimero = primero.getHoraFin();
        LocalTime inicioSegundo = segundo.getHoraInicio();
        LocalTime finSegundo = segundo.getHoraFin();

        return inicioPrimero.isBefore(finSegundo) && finPrimero.isAfter(inicioSegundo);
    }

    private void validarCuposDisponibles(List<MatriculaDetalle> detalles) {
        Set<String> horariosValidados = new HashSet<>();

        for (MatriculaDetalle detalle : detalles) {
            Horario horario = detalle.getHorario();
            String claveHorarioDia = horario.getId() + "-" + detalle.getDiaSemana();
            if (!horariosValidados.add(claveHorarioDia)) {
                continue;
            }

            if (horario.getCupoMaximo() == null || horario.getCupoMaximo() <= 0) {
                throw new IllegalArgumentException("El horario no tiene cupo maximo configurado");
            }

            long matriculadosActuales = matriculaDetalleRepository.countByHorarioIdAndDiaSemanaAndEstado(
                    horario.getId(),
                    detalle.getDiaSemana(),
                    EstadoMatriculaDetalle.ACTIVO
            );
            long nuevosEnHorario = detalles.stream()
                    .filter(detalleActual -> detalleActual.getHorario().getId().equals(horario.getId()))
                    .filter(detalleActual -> detalleActual.getDiaSemana().equals(detalle.getDiaSemana()))
                    .count();

            if (matriculadosActuales + nuevosEnHorario > horario.getCupoMaximo()) {
                throw new IllegalArgumentException("El horario ya alcanzo su cupo maximo de matriculados");
            }
        }
    }

    private void validarDeportesActivosDelCliente(Usuario usuario, List<MatriculaDetalle> detalles) {
        Set<UUID> deportesValidados = new HashSet<>();

        for (MatriculaDetalle detalle : detalles) {
            UUID deporteId = detalle.getDeporte().getId();
            if (!deportesValidados.add(deporteId)) {
                continue;
            }

            boolean tieneMatriculaActiva = matriculaDetalleRepository.existsByMatriculaUsuarioIdAndDeporteIdAndEstado(
                    usuario.getId(),
                    deporteId,
                    EstadoMatriculaDetalle.ACTIVO
            );
            if (tieneMatriculaActiva) {
                throw new IllegalArgumentException("El cliente ya tiene una matricula activa en este deporte");
            }
        }
    }

    private void crearPagoAutomatico(Matricula matricula) {
        BigDecimal precioDoceSesiones = precioMatriculaRepository.findFirstByActivoTrueOrderByCreatedAtDesc()
                .map(precio -> precio.getPrecioDoceSesiones())
                .orElse(PRECIO_DEFAULT_DOCE_SESIONES);

        long cantidadDeportes = matricula.getDetalles()
                .stream()
                .map(detalle -> detalle.getDeporte().getId())
                .distinct()
                .count();
        BigDecimal montoTotal = precioDoceSesiones.multiply(BigDecimal.valueOf(cantidadDeportes));

        Pago pago = new Pago();
        pago.setMatricula(matricula);
        pago.setMontoTotal(montoTotal);
        pago.setMontoPagado(BigDecimal.ZERO);
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setObservaciones("Pago generado automaticamente por matricula");

        pagoRepository.save(pago);
    }

    private MatriculaResponseDTO toResponseDTO(Matricula matricula) {
        MatriculaResponseDTO dto = modelMapper.map(matricula, MatriculaResponseDTO.class);
        dto.setUsuarioId(matricula.getUsuario().getId());
        dto.setUsuarioNombre(matricula.getUsuario().getNombre() + " " + matricula.getUsuario().getApellido());
        dto.setDetalles(matricula.getDetalles()
                .stream()
                .map(this::toDetalleResponseDTO)
                .toList());
        return dto;
    }

    private MatriculaDetalleResponseDTO toDetalleResponseDTO(MatriculaDetalle detalle) {
        MatriculaDetalleResponseDTO dto = modelMapper.map(detalle, MatriculaDetalleResponseDTO.class);
        dto.setDeporteId(detalle.getDeporte().getId());
        dto.setDeporteNombre(detalle.getDeporte().getNombre());
        dto.setHorarioId(detalle.getHorario().getId());
        dto.setCampoId(detalle.getHorario().getCampo().getId());
        dto.setCampoNombre(detalle.getHorario().getCampo().getNombre());
        dto.setDiaSemana(detalle.getDiaSemana());
        dto.setHoraInicio(detalle.getHorario().getHoraInicio());
        dto.setHoraFin(detalle.getHorario().getHoraFin());
        dto.setTurno(detalle.getTurno());
        dto.setSesionesRestantes(detalle.getTotalSesiones() - detalle.getSesionesConsumidas());
        return dto;
    }

    private int contarDeportesUnicos(List<MatriculaDetalleCreateDTO> detalles) {
        return (int) detalles.stream()
                .map(MatriculaDetalleCreateDTO::getDeporteId)
                .distinct()
                .count();
    }

    private TurnoHorario normalizarTurno(String turno) {
        if (turno == null || turno.isBlank()) {
            throw new IllegalArgumentException("El turno es obligatorio");
        }

        String turnoNormalizado = Normalizer.normalize(turno.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase(Locale.ROOT);
        if (turnoNormalizado.contains("MANANA")) {
            return TurnoHorario.MANANA;
        }
        if (turnoNormalizado.contains("TARDE")) {
            return TurnoHorario.TARDE;
        }
        throw new IllegalArgumentException("El turno debe ser MANANA o TARDE");
    }

    private TurnoMatriculaDetalle toTurnoMatriculaDetalle(TurnoHorario turno) {
        return switch (turno) {
            case MANANA -> TurnoMatriculaDetalle.MANANA;
            case TARDE -> TurnoMatriculaDetalle.TARDE;
        };
    }
}
