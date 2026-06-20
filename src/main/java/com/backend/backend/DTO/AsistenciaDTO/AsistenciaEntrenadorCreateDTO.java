package com.backend.backend.DTO.AsistenciaDTO;

import com.backend.backend.Entity.EstadoAsistencia;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class AsistenciaEntrenadorCreateDTO {

    @NotNull(message = "El ID de la asignacion de entrenador es obligatorio")
    private UUID horarioEntrenadorId;

    private LocalDate fechaAsistencia;

    @NotNull(message = "El estado de asistencia es obligatorio")
    private EstadoAsistencia estado;

    private String observaciones;

    public UUID getHorarioEntrenadorId() { return horarioEntrenadorId; }
    public void setHorarioEntrenadorId(UUID horarioEntrenadorId) { this.horarioEntrenadorId = horarioEntrenadorId; }

    public LocalDate getFechaAsistencia() { return fechaAsistencia; }
    public void setFechaAsistencia(LocalDate fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public EstadoAsistencia getEstado() { return estado; }
    public void setEstado(EstadoAsistencia estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
