package com.backend.backend.DTO.MatriculaDTO;

import com.backend.backend.Entity.EstadoMatricula;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MatriculaResponseDTO {

    private UUID id;
    private UUID usuarioId;
    private String usuarioNombre;
    private LocalDate fechaMatricula;
    private EstadoMatricula estado;
    private Integer totalSesiones;
    private Integer sesionesConsumidas;
    private String observaciones;
    private List<MatriculaDetalleResponseDTO> detalles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }

    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public EstadoMatricula getEstado() { return estado; }
    public void setEstado(EstadoMatricula estado) { this.estado = estado; }

    public Integer getTotalSesiones() { return totalSesiones; }
    public void setTotalSesiones(Integer totalSesiones) { this.totalSesiones = totalSesiones; }

    public Integer getSesionesConsumidas() { return sesionesConsumidas; }
    public void setSesionesConsumidas(Integer sesionesConsumidas) { this.sesionesConsumidas = sesionesConsumidas; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<MatriculaDetalleResponseDTO> getDetalles() { return detalles; }
    public void setDetalles(List<MatriculaDetalleResponseDTO> detalles) { this.detalles = detalles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
