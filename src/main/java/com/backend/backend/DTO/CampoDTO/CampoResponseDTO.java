package com.backend.backend.DTO.CampoDTO;

import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;
@JsonPropertyOrder({
        "id",
        "nombre",
        "descripcion",
        "activo",
        "deporte",
        "createdAt",
        "updatedAt"
})
public class CampoResponseDTO {

    private UUID id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private DeporteResponseDTO deporte; // 👈 objeto completo, no solo ids
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CampoResponseDTO() {}

    public CampoResponseDTO(UUID id, String nombre, String descripcion, Boolean activo,
                            DeporteResponseDTO deporte,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.deporte = deporte;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public DeporteResponseDTO getDeporte() { return deporte; }
    public void setDeporte(DeporteResponseDTO deporte) { this.deporte = deporte; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}