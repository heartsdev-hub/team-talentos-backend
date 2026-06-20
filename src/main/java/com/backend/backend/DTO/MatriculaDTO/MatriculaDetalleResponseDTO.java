package com.backend.backend.DTO.MatriculaDTO;

import com.backend.backend.Entity.EstadoMatriculaDetalle;
import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.TurnoMatriculaDetalle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class MatriculaDetalleResponseDTO {

    private UUID id;
    private UUID deporteId;
    private String deporteNombre;
    private UUID horarioId;
    private UUID campoId;
    private String campoNombre;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private TurnoMatriculaDetalle turno;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer totalSesiones;
    private Integer sesionesConsumidas;
    private Integer sesionesRestantes;
    private EstadoMatriculaDetalle estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getDeporteId() { return deporteId; }
    public void setDeporteId(UUID deporteId) { this.deporteId = deporteId; }

    public String getDeporteNombre() { return deporteNombre; }
    public void setDeporteNombre(String deporteNombre) { this.deporteNombre = deporteNombre; }

    public UUID getHorarioId() { return horarioId; }
    public void setHorarioId(UUID horarioId) { this.horarioId = horarioId; }

    public UUID getCampoId() { return campoId; }
    public void setCampoId(UUID campoId) { this.campoId = campoId; }

    public String getCampoNombre() { return campoNombre; }
    public void setCampoNombre(String campoNombre) { this.campoNombre = campoNombre; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public TurnoMatriculaDetalle getTurno() { return turno; }
    public void setTurno(TurnoMatriculaDetalle turno) { this.turno = turno; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Integer getTotalSesiones() { return totalSesiones; }
    public void setTotalSesiones(Integer totalSesiones) { this.totalSesiones = totalSesiones; }

    public Integer getSesionesConsumidas() { return sesionesConsumidas; }
    public void setSesionesConsumidas(Integer sesionesConsumidas) { this.sesionesConsumidas = sesionesConsumidas; }

    public Integer getSesionesRestantes() { return sesionesRestantes; }
    public void setSesionesRestantes(Integer sesionesRestantes) { this.sesionesRestantes = sesionesRestantes; }

    public EstadoMatriculaDetalle getEstado() { return estado; }
    public void setEstado(EstadoMatriculaDetalle estado) { this.estado = estado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
