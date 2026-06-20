package com.backend.backend.DTO.AsistenciaDTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public class AsistenciaClienteJustificarDTO {

    @NotBlank(message = "La justificacion es obligatoria")
    private String justificacion;

    private UUID horarioReprogramadoId;
    private LocalDate fechaReprogramada;

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public UUID getHorarioReprogramadoId() { return horarioReprogramadoId; }
    public void setHorarioReprogramadoId(UUID horarioReprogramadoId) { this.horarioReprogramadoId = horarioReprogramadoId; }

    public LocalDate getFechaReprogramada() { return fechaReprogramada; }
    public void setFechaReprogramada(LocalDate fechaReprogramada) { this.fechaReprogramada = fechaReprogramada; }
}
