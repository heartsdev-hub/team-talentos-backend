package com.backend.backend.DTO.AsistenciaDTO;

import com.backend.backend.Entity.EstadoAsistencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AsistenciaResponseDTO {

    private UUID id;
    private LocalDate fechaAsistencia;
    private EstadoAsistencia estado;
    private String justificacion;
    private UUID horarioReprogramadoId;
    private LocalDate fechaReprogramada;
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getFechaAsistencia() { return fechaAsistencia; }
    public void setFechaAsistencia(LocalDate fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public EstadoAsistencia getEstado() { return estado; }
    public void setEstado(EstadoAsistencia estado) { this.estado = estado; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public UUID getHorarioReprogramadoId() { return horarioReprogramadoId; }
    public void setHorarioReprogramadoId(UUID horarioReprogramadoId) { this.horarioReprogramadoId = horarioReprogramadoId; }

    public LocalDate getFechaReprogramada() { return fechaReprogramada; }
    public void setFechaReprogramada(LocalDate fechaReprogramada) { this.fechaReprogramada = fechaReprogramada; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
