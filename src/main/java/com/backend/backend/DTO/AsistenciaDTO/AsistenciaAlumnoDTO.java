package com.backend.backend.DTO.AsistenciaDTO;

import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.EstadoAsistencia;
import com.backend.backend.Entity.TurnoMatriculaDetalle;

import java.time.LocalTime;
import java.util.UUID;

public class AsistenciaAlumnoDTO {

    private UUID matriculaDetalleId;
    private UUID matriculaId;
    private UUID usuarioId;
    private String nombre;
    private String apellido;
    private String dni;
    private String numCelular;
    private String email;
    private UUID deporteId;
    private String deporteNombre;
    private UUID horarioId;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private DiaSemana diaSemana;
    private TurnoMatriculaDetalle turno;
    private Integer sesionesRestantes;
    private Boolean asistio;
    private UUID asistenciaId;
    private EstadoAsistencia estadoAsistencia;

    public UUID getMatriculaDetalleId() { return matriculaDetalleId; }
    public void setMatriculaDetalleId(UUID matriculaDetalleId) { this.matriculaDetalleId = matriculaDetalleId; }

    public UUID getMatriculaId() { return matriculaId; }
    public void setMatriculaId(UUID matriculaId) { this.matriculaId = matriculaId; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNumCelular() { return numCelular; }
    public void setNumCelular(String numCelular) { this.numCelular = numCelular; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UUID getDeporteId() { return deporteId; }
    public void setDeporteId(UUID deporteId) { this.deporteId = deporteId; }

    public String getDeporteNombre() { return deporteNombre; }
    public void setDeporteNombre(String deporteNombre) { this.deporteNombre = deporteNombre; }

    public UUID getHorarioId() { return horarioId; }
    public void setHorarioId(UUID horarioId) { this.horarioId = horarioId; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public TurnoMatriculaDetalle getTurno() { return turno; }
    public void setTurno(TurnoMatriculaDetalle turno) { this.turno = turno; }

    public Integer getSesionesRestantes() { return sesionesRestantes; }
    public void setSesionesRestantes(Integer sesionesRestantes) { this.sesionesRestantes = sesionesRestantes; }

    public Boolean getAsistio() { return asistio; }
    public void setAsistio(Boolean asistio) { this.asistio = asistio; }

    public UUID getAsistenciaId() { return asistenciaId; }
    public void setAsistenciaId(UUID asistenciaId) { this.asistenciaId = asistenciaId; }

    public EstadoAsistencia getEstadoAsistencia() { return estadoAsistencia; }
    public void setEstadoAsistencia(EstadoAsistencia estadoAsistencia) { this.estadoAsistencia = estadoAsistencia; }
}
