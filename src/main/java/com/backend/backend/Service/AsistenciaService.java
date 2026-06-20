package com.backend.backend.Service;

import com.backend.backend.DTO.AsistenciaDTO.AsistenciaAlumnoDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaClienteContextoDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaClienteCreateDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaClienteJustificarDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaDeporteDisponibleDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaEntrenadorCreateDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaHorarioDisponibleDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaResponseDTO;
import com.backend.backend.Entity.AsistenciaCliente;
import com.backend.backend.Entity.AsistenciaEntrenador;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.EstadoAsistencia;
import com.backend.backend.Entity.EstadoMatriculaDetalle;
import com.backend.backend.Entity.Horario;
import com.backend.backend.Entity.HorarioEntrenador;
import com.backend.backend.Entity.MatriculaDetalle;
import com.backend.backend.Entity.TurnoHorario;
import com.backend.backend.Exception.ResourceAlreadyExistsException;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.AsistenciaClienteRepository;
import com.backend.backend.Repository.AsistenciaEntrenadorRepository;
import com.backend.backend.Repository.HorarioEntrenadorRepository;
import com.backend.backend.Repository.HorarioRepository;
import com.backend.backend.Repository.MatriculaDetalleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class AsistenciaService {

    private static final ZoneId ZONA_PERU = ZoneId.of("America/Lima");
    private static final LocalTime HORA_INICIO_TARDE = LocalTime.NOON;

    private final AsistenciaClienteRepository asistenciaClienteRepository;
    private final AsistenciaEntrenadorRepository asistenciaEntrenadorRepository;
    private final MatriculaDetalleRepository matriculaDetalleRepository;
    private final HorarioEntrenadorRepository horarioEntrenadorRepository;
    private final HorarioRepository horarioRepository;
    private final ModelMapper modelMapper;

    public AsistenciaService(AsistenciaClienteRepository asistenciaClienteRepository,
                             AsistenciaEntrenadorRepository asistenciaEntrenadorRepository,
                             MatriculaDetalleRepository matriculaDetalleRepository,
                             HorarioEntrenadorRepository horarioEntrenadorRepository,
                             HorarioRepository horarioRepository,
                             ModelMapper modelMapper) {
        this.asistenciaClienteRepository = asistenciaClienteRepository;
        this.asistenciaEntrenadorRepository = asistenciaEntrenadorRepository;
        this.matriculaDetalleRepository = matriculaDetalleRepository;
        this.horarioEntrenadorRepository = horarioEntrenadorRepository;
        this.horarioRepository = horarioRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public AsistenciaClienteContextoDTO obtenerContextoCliente() {
        LocalDate fecha = LocalDate.now(ZONA_PERU);
        LocalTime horaActual = LocalTime.now(ZONA_PERU);

        AsistenciaClienteContextoDTO dto = new AsistenciaClienteContextoDTO();
        dto.setFecha(fecha);
        dto.setHoraActual(horaActual);
        dto.setDiaSemana(toDiaSemana(fecha));
        dto.setTurnoActual(toTurnoActual(horaActual));
        return dto;
    }

    @Transactional(readOnly = true)
    public List<AsistenciaDeporteDisponibleDTO> listarDeportesCliente(LocalDate fecha, String turno) {
        DiaSemana diaSemana = toDiaSemana(fechaOrToday(fecha));
        TurnoHorario turnoHorario = toTurnoHorario(turno);

        return horarioRepository.findDeportesDisponiblesParaAsistencia(diaSemana, turnoHorario)
                .stream()
                .map(this::toDeporteDisponibleDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AsistenciaHorarioDisponibleDTO> listarHorariosCliente(UUID deporteId, LocalDate fecha, String turno) {
        DiaSemana diaSemana = toDiaSemana(fechaOrToday(fecha));
        TurnoHorario turnoHorario = toTurnoHorario(turno);

        return horarioRepository.findHorariosDisponiblesParaAsistencia(deporteId, diaSemana, turnoHorario)
                .stream()
                .map(horario -> toHorarioDisponibleDTO(horario, diaSemana))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AsistenciaAlumnoDTO> listarAlumnosCliente(UUID horarioId, LocalDate fecha) {
        LocalDate fechaAsistencia = fechaOrToday(fecha);
        DiaSemana diaSemana = toDiaSemana(fechaAsistencia);

        return matriculaDetalleRepository.findAlumnosParaAsistencia(
                        horarioId,
                        diaSemana,
                        EstadoMatriculaDetalle.ACTIVO
                )
                .stream()
                .map(detalle -> toAsistenciaAlumnoDTO(detalle, fechaAsistencia))
                .toList();
    }

    @Transactional
    public AsistenciaResponseDTO registrarCliente(AsistenciaClienteCreateDTO dto) {
        LocalDate fecha = dto.getFechaAsistencia() != null ? dto.getFechaAsistencia() : LocalDate.now();
        if (asistenciaClienteRepository.existsByMatriculaDetalleIdAndFechaAsistencia(dto.getMatriculaDetalleId(), fecha)) {
            throw new ResourceAlreadyExistsException("Ya existe asistencia registrada para este cliente en esa fecha");
        }

        MatriculaDetalle detalle = matriculaDetalleRepository.findById(dto.getMatriculaDetalleId())
                .orElseThrow(() -> new ResourceNotFoundException("Detalle de matricula no encontrado"));

        AsistenciaCliente asistencia = new AsistenciaCliente();
        asistencia.setMatriculaDetalle(detalle);
        asistencia.setFechaAsistencia(fecha);
        asistencia.setEstado(dto.getEstado());

        if (dto.getEstado() == EstadoAsistencia.ASISTIO) {
            consumirSesion(detalle);
        }

        return toClienteResponseDTO(asistenciaClienteRepository.save(asistencia));
    }

    @Transactional
    public AsistenciaResponseDTO justificarCliente(UUID asistenciaId, AsistenciaClienteJustificarDTO dto) {
        AsistenciaCliente asistencia = asistenciaClienteRepository.findById(asistenciaId)
                .orElseThrow(() -> new ResourceNotFoundException("Asistencia no encontrada"));

        if (asistencia.getEstado() != EstadoAsistencia.FALTO) {
            throw new IllegalArgumentException("Solo se puede justificar una falta");
        }

        asistencia.setEstado(EstadoAsistencia.JUSTIFICADA);
        asistencia.setJustificacion(dto.getJustificacion());

        if (dto.getHorarioReprogramadoId() != null) {
            Horario horario = horarioRepository.findById(dto.getHorarioReprogramadoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Horario de reprogramacion no encontrado"));
            validarHorarioReprogramado(asistencia.getMatriculaDetalle(), horario);
            asistencia.setHorarioReprogramado(horario);
            asistencia.setFechaReprogramada(dto.getFechaReprogramada());
        }

        return toClienteResponseDTO(asistenciaClienteRepository.save(asistencia));
    }

    @Transactional
    public AsistenciaResponseDTO registrarEntrenador(AsistenciaEntrenadorCreateDTO dto) {
        LocalDate fecha = dto.getFechaAsistencia() != null ? dto.getFechaAsistencia() : LocalDate.now();
        if (asistenciaEntrenadorRepository.existsByHorarioEntrenadorIdAndFechaAsistencia(dto.getHorarioEntrenadorId(), fecha)) {
            throw new ResourceAlreadyExistsException("Ya existe asistencia registrada para este entrenador en esa fecha");
        }

        HorarioEntrenador horarioEntrenador = horarioEntrenadorRepository.findById(dto.getHorarioEntrenadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Asignacion de entrenador no encontrada"));

        AsistenciaEntrenador asistencia = new AsistenciaEntrenador();
        asistencia.setHorarioEntrenador(horarioEntrenador);
        asistencia.setFechaAsistencia(fecha);
        asistencia.setEstado(dto.getEstado());
        asistencia.setObservaciones(dto.getObservaciones());

        return modelMapper.map(asistenciaEntrenadorRepository.save(asistencia), AsistenciaResponseDTO.class);
    }

    private void consumirSesion(MatriculaDetalle detalle) {
        if (detalle.getSesionesConsumidas() >= detalle.getTotalSesiones()) {
            throw new IllegalArgumentException("El cliente ya completo las 12 sesiones de este deporte");
        }

        detalle.setSesionesConsumidas(detalle.getSesionesConsumidas() + 1);
        if (detalle.getSesionesConsumidas().equals(detalle.getTotalSesiones())) {
            detalle.setEstado(EstadoMatriculaDetalle.FINALIZADO);
        }
        matriculaDetalleRepository.save(detalle);
    }

    private void validarHorarioReprogramado(MatriculaDetalle detalle, Horario horario) {
        if (!horario.getCampo().getDeporte().getId().equals(detalle.getDeporte().getId())) {
            throw new IllegalArgumentException("La reprogramacion debe ser en un horario del mismo deporte");
        }
    }

    private AsistenciaResponseDTO toClienteResponseDTO(AsistenciaCliente asistencia) {
        AsistenciaResponseDTO dto = modelMapper.map(asistencia, AsistenciaResponseDTO.class);
        if (asistencia.getHorarioReprogramado() != null) {
            dto.setHorarioReprogramadoId(asistencia.getHorarioReprogramado().getId());
        }
        return dto;
    }

    private LocalDate fechaOrToday(LocalDate fecha) {
        return fecha != null ? fecha : LocalDate.now(ZONA_PERU);
    }

    private TurnoHorario toTurnoActual(LocalTime hora) {
        return hora.isBefore(HORA_INICIO_TARDE) ? TurnoHorario.MANANA : TurnoHorario.TARDE;
    }

    private TurnoHorario toTurnoHorario(String turno) {
        if (turno == null || turno.isBlank()) {
            return toTurnoActual(LocalTime.now(ZONA_PERU));
        }

        String normalized = turno.trim()
                .toUpperCase()
                .replace("Ñ", "N");

        return TurnoHorario.valueOf(normalized);
    }

    private DiaSemana toDiaSemana(LocalDate fecha) {
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        return switch (dayOfWeek) {
            case MONDAY -> DiaSemana.LUNES;
            case TUESDAY -> DiaSemana.MARTES;
            case WEDNESDAY -> DiaSemana.MIERCOLES;
            case THURSDAY -> DiaSemana.JUEVES;
            case FRIDAY -> DiaSemana.VIERNES;
            case SATURDAY -> DiaSemana.SABADO;
            case SUNDAY -> throw new IllegalArgumentException("El club atiende de lunes a sabado");
        };
    }

    private AsistenciaDeporteDisponibleDTO toDeporteDisponibleDTO(Deporte deporte) {
        AsistenciaDeporteDisponibleDTO dto = new AsistenciaDeporteDisponibleDTO();
        dto.setDeporteId(deporte.getId());
        dto.setDeporteNombre(deporte.getNombre());
        return dto;
    }

    private AsistenciaHorarioDisponibleDTO toHorarioDisponibleDTO(Horario horario, DiaSemana diaSemana) {
        AsistenciaHorarioDisponibleDTO dto = new AsistenciaHorarioDisponibleDTO();
        dto.setHorarioId(horario.getId());
        dto.setCampoId(horario.getCampo().getId());
        dto.setCampoNombre(horario.getCampo().getNombre());
        dto.setDeporteId(horario.getCampo().getDeporte().getId());
        dto.setDeporteNombre(horario.getCampo().getDeporte().getNombre());
        dto.setDiaSemana(horario.getDiaSemana());
        dto.setTurno(horario.getTurno());
        dto.setHoraInicio(horario.getHoraInicio());
        dto.setHoraFin(horario.getHoraFin());
        dto.setCupoMaximo(horario.getCupoMaximo());
        dto.setMatriculados(matriculaDetalleRepository.countByHorarioIdAndDiaSemanaAndEstado(
                horario.getId(),
                diaSemana,
                EstadoMatriculaDetalle.ACTIVO
        ));
        return dto;
    }

    private AsistenciaAlumnoDTO toAsistenciaAlumnoDTO(MatriculaDetalle detalle, LocalDate fechaAsistencia) {
        AsistenciaAlumnoDTO dto = new AsistenciaAlumnoDTO();
        dto.setMatriculaDetalleId(detalle.getId());
        dto.setMatriculaId(detalle.getMatricula().getId());
        dto.setUsuarioId(detalle.getMatricula().getUsuario().getId());
        dto.setNombre(detalle.getMatricula().getUsuario().getNombre());
        dto.setApellido(detalle.getMatricula().getUsuario().getApellido());
        dto.setDni(detalle.getMatricula().getUsuario().getDni());
        dto.setNumCelular(detalle.getMatricula().getUsuario().getNumCelular());
        dto.setEmail(detalle.getMatricula().getUsuario().getEmail());
        dto.setDeporteId(detalle.getDeporte().getId());
        dto.setDeporteNombre(detalle.getDeporte().getNombre());
        dto.setHorarioId(detalle.getHorario().getId());
        dto.setHoraInicio(detalle.getHorario().getHoraInicio());
        dto.setHoraFin(detalle.getHorario().getHoraFin());
        dto.setDiaSemana(detalle.getDiaSemana());
        dto.setTurno(detalle.getTurno());
        dto.setSesionesRestantes(detalle.getTotalSesiones() - detalle.getSesionesConsumidas());

        asistenciaClienteRepository.findByMatriculaDetalleIdAndFechaAsistencia(detalle.getId(), fechaAsistencia)
                .ifPresentOrElse(asistencia -> {
                    dto.setAsistenciaId(asistencia.getId());
                    dto.setEstadoAsistencia(asistencia.getEstado());
                    dto.setAsistio(asistencia.getEstado() == EstadoAsistencia.ASISTIO);
                }, () -> dto.setAsistio(false));

        return dto;
    }
}
