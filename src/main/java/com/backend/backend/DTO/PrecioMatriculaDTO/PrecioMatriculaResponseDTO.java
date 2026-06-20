package com.backend.backend.DTO.PrecioMatriculaDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PrecioMatriculaResponseDTO {

    private UUID id;
    private BigDecimal precioDoceSesiones;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public BigDecimal getPrecioDoceSesiones() { return precioDoceSesiones; }
    public void setPrecioDoceSesiones(BigDecimal precioDoceSesiones) { this.precioDoceSesiones = precioDoceSesiones; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
