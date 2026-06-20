package com.backend.backend.Service;

import com.backend.backend.DTO.HorarioDTO.HorarioCreateDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioEntrenadorCreateDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioEntrenadorResponseDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioResponseDTO;
import com.backend.backend.Entity.Campo;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.EstadoMatriculaDetalle;
import com.backend.backend.Entity.Horario;
import com.backend.backend.Entity.HorarioEntrenador;
import com.backend.backend.Entity.TurnoHorario;
import com.backend.backend.Entity.Usuario;
import com.backend.backend.Exception.ResourceAlreadyExistsException;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.CampoRepository;
import com.backend.backend.Repository.HorarioEntrenadorRepository;
import com.backend.backend.Repository.HorarioRepository;
import com.backend.backend.Repository.MatriculaDetalleRepository;
import com.backend.backend.Repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class HorarioService {

    private static final String ROL_ENTRENADOR = "ENTRENADOR";

    private final HorarioRepository horarioRepository;
    private final HorarioEntrenadorRepository horarioEntrenadorRepository;
    private final CampoRepository campoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MatriculaDetalleRepository matriculaDetalleRepository;
    private final ModelMapper modelMapper;

    public HorarioService(HorarioRepository horarioRepository,
                          HorarioEntrenadorRepository horarioEntrenadorRepository,
                          CampoRepository campoRepository,
                          UsuarioRepository usuarioRepository,
                          MatriculaDetalleRepository matriculaDetalleRepository,
                          ModelMapper modelMapper) {
        this.horarioRepository = horarioRepository;
        this.horarioEntrenadorRepository = horarioEntrenadorRepository;
        this.campoRepository = campoRepository;
        this.usuarioRepository = usuarioRepository;
        this.matriculaDetalleRepository = matriculaDetalleRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<HorarioResponseDTO> crear(HorarioCreateDTO dto) {
        validarHoras(dto);
        TurnoHorario turno = normalizarTurno(dto.getTurno());

        Campo campo = campoRepository.findById(dto.getCampoId())
                .orElseThrow(() -> new ResourceNotFoundException("Campo no encontrado con ID: " + dto.getCampoId()));
        Usuario entrenador = usuarioRepository.findById(dto.getEntrenadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Entrenador no encontrado con ID: " + dto.getEntrenadorId()));

        if (!tieneRolEntrenador(entrenador)) {
            throw new IllegalArgumentException("Solo los usuarios con rol ENTRENADOR pueden asignarse a un horario");
        }
        validarEntrenadorDelDeporte(campo, entrenador);

        return Arrays.stream(DiaSemana.values())
                .map(diaSemana -> crearHorarioDelDia(dto, campo, entrenador, turno, diaSemana))
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public HorarioResponseDTO asignarEntrenador(UUID horarioId, HorarioEntrenadorCreateDTO dto) {
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con ID: " + horarioId));

        Usuario entrenador = usuarioRepository.findById(dto.getEntrenadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Entrenador no encontrado con ID: " + dto.getEntrenadorId()));

        if (!tieneRolEntrenador(entrenador)) {
            throw new IllegalArgumentException("Solo los usuarios con rol ENTRENADOR pueden asignarse a un horario");
        }
        validarEntrenadorDelDeporte(horario, entrenador);

        LocalDate fechaInicio = dto.getFechaInicio() != null ? dto.getFechaInicio() : LocalDate.now();
        if (dto.getFechaFin() != null && dto.getFechaFin().isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha fin no puede ser menor que la fecha inicio");
        }

        if (horarioEntrenadorRepository.existsByHorarioIdAndEntrenadorIdAndFechaInicio(
                horarioId, dto.getEntrenadorId(), fechaInicio)) {
            throw new ResourceAlreadyExistsException("El entrenador ya esta asignado a este horario desde esa fecha");
        }

        HorarioEntrenador horarioEntrenador = new HorarioEntrenador();
        horarioEntrenador.setHorario(horario);
        horarioEntrenador.setEntrenador(entrenador);
        horarioEntrenador.setFechaInicio(fechaInicio);
        horarioEntrenador.setFechaFin(dto.getFechaFin());
        horarioEntrenador.setActivo(true);

        horarioEntrenadorRepository.save(horarioEntrenador);
        horario.getEntrenadores().add(horarioEntrenador);

        return toResponseDTO(horario);
    }

    @Transactional(readOnly = true)
    public List<HorarioResponseDTO> listarTodos() {
        return horarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<HorarioResponseDTO> listarPorCampo(UUID campoId) {
        return horarioRepository.findByCampoId(campoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private void validarHoras(HorarioCreateDTO dto) {
        if (!dto.getHoraFin().isAfter(dto.getHoraInicio())) {
            throw new IllegalArgumentException("La hora fin debe ser mayor que la hora inicio");
        }
    }

    private TurnoHorario normalizarTurno(String turno) {
        String turnoNormalizado = turno.trim().toUpperCase(Locale.ROOT);
        if ("MANANA".equals(turnoNormalizado)) {
            return TurnoHorario.MANANA;
        }
        if ("TARDE".equals(turnoNormalizado)) {
            return TurnoHorario.TARDE;
        }
        throw new IllegalArgumentException("El turno debe ser MANANA o TARDE");
    }

    private boolean tieneRolEntrenador(Usuario usuario) {
        return usuario.getRoles()
                .stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase(ROL_ENTRENADOR));
    }

    private Horario crearHorarioDelDia(HorarioCreateDTO dto,
                                       Campo campo,
                                       Usuario entrenador,
                                       TurnoHorario turno,
                                       DiaSemana diaSemana) {
        if (horarioRepository.existsByCampoIdAndDiaSemanaAndHoraInicioAndHoraFin(
                dto.getCampoId(), diaSemana, dto.getHoraInicio(), dto.getHoraFin())) {
            throw new ResourceAlreadyExistsException("Ya existe un horario para ese campo, dia y rango de horas");
        }
        if (horarioRepository.existsHorarioCruzado(
                dto.getCampoId(), diaSemana, dto.getHoraInicio(), dto.getHoraFin())) {
            throw new IllegalArgumentException("El horario se cruza con otro horario del mismo campo y dia");
        }

        Horario horario = new Horario();
        horario.setCampo(campo);
        horario.setDiaSemana(diaSemana);
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());
        horario.setTurno(turno);
        horario.setCupoMaximo(dto.getCupoMaximo());
        horario.setActivo(true);

        Horario horarioGuardado = horarioRepository.save(horario);

        HorarioEntrenador horarioEntrenador = new HorarioEntrenador();
        horarioEntrenador.setHorario(horarioGuardado);
        horarioEntrenador.setEntrenador(entrenador);
        horarioEntrenador.setFechaInicio(LocalDate.now());
        horarioEntrenador.setActivo(true);

        horarioEntrenadorRepository.save(horarioEntrenador);
        horarioGuardado.getEntrenadores().add(horarioEntrenador);

        return horarioGuardado;
    }

    private void validarEntrenadorDelDeporte(Campo campo, Usuario entrenador) {
        if (campo == null || campo.getDeporte() == null) {
            throw new IllegalArgumentException("El campo no tiene deporte asociado");
        }

        Deporte deporteDelCampo = campo.getDeporte();
        boolean ensenaDeporte = entrenador.getDeportesEntrenados()
                .stream()
                .anyMatch(deporte -> deporte.getId().equals(deporteDelCampo.getId()));

        if (!ensenaDeporte) {
            throw new IllegalArgumentException("El entrenador no esta asignado al deporte de este campo");
        }
    }

    private void validarEntrenadorDelDeporte(Horario horario, Usuario entrenador) {
        if (horario.getCampo() == null || horario.getCampo().getDeporte() == null) {
            throw new IllegalArgumentException("El horario no tiene un campo/deporte asociado");
        }

        Deporte deporteDelHorario = horario.getCampo().getDeporte();
        boolean ensenaDeporte = entrenador.getDeportesEntrenados()
                .stream()
                .anyMatch(deporte -> deporte.getId().equals(deporteDelHorario.getId()));

        if (!ensenaDeporte) {
            throw new IllegalArgumentException("El entrenador no esta asignado al deporte de este horario");
        }
    }

    private HorarioResponseDTO toResponseDTO(Horario horario) {
        HorarioResponseDTO dto = modelMapper.map(horario, HorarioResponseDTO.class);
        dto.setCampoId(horario.getCampo().getId());
        dto.setCampoNombre(horario.getCampo().getNombre());
        if (horario.getCampo().getDeporte() != null) {
            dto.setDeporteId(horario.getCampo().getDeporte().getId());
            dto.setDeporteNombre(horario.getCampo().getDeporte().getNombre());
        }

        long matriculadosActuales = matriculaDetalleRepository.countByHorarioIdAndEstado(
                horario.getId(),
                EstadoMatriculaDetalle.ACTIVO
        );
        dto.setMatriculadosActuales(matriculadosActuales);
        dto.setCuposDisponibles(calcularCuposDisponibles(horario, matriculadosActuales));
        dto.setEntrenadores(horario.getEntrenadores()
                .stream()
                .map(this::toEntrenadorResponseDTO)
                .toList());
        return dto;
    }

    private Integer calcularCuposDisponibles(Horario horario, long matriculadosActuales) {
        if (horario.getCupoMaximo() == null) {
            return null;
        }
        long disponibles = horario.getCupoMaximo() - matriculadosActuales;
        return (int) Math.max(disponibles, 0);
    }

    private HorarioEntrenadorResponseDTO toEntrenadorResponseDTO(HorarioEntrenador horarioEntrenador) {
        HorarioEntrenadorResponseDTO dto = modelMapper.map(horarioEntrenador, HorarioEntrenadorResponseDTO.class);
        Usuario entrenador = horarioEntrenador.getEntrenador();
        dto.setEntrenadorId(entrenador.getId());
        dto.setEntrenadorNombre(entrenador.getNombre() + " " + entrenador.getApellido());
        return dto;
    }
}
