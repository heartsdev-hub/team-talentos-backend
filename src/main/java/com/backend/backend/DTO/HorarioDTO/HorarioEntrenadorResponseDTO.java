package com.backend.backend.DTO.HorarioDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class HorarioEntrenadorResponseDTO {

    private UUID id;
    private UUID entrenadorId;
    private String entrenadorNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getEntrenadorId() { return entrenadorId; }
    public void setEntrenadorId(UUID entrenadorId) { this.entrenadorId = entrenadorId; }

    public String getEntrenadorNombre() { return entrenadorNombre; }
    public void setEntrenadorNombre(String entrenadorNombre) { this.entrenadorNombre = entrenadorNombre; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
