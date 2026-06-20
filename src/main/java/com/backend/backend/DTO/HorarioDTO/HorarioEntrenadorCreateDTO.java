package com.backend.backend.DTO.HorarioDTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class HorarioEntrenadorCreateDTO {

    @NotNull(message = "El ID del entrenador es obligatorio")
    private UUID entrenadorId;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public UUID getEntrenadorId() { return entrenadorId; }
    public void setEntrenadorId(UUID entrenadorId) { this.entrenadorId = entrenadorId; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}
