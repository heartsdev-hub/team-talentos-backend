package com.backend.backend.DTO.DeporteDTO;

import com.backend.backend.DTO.CampoDTO.CampoResponseDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@JsonPropertyOrder({
        "id",
        "nombre",
        "descripcion",
        "activo",
        "campo",
        "entrenadores",
        "createdAt",
        "updatedAt"
})
public class DeporteResponseDTO {

    private UUID id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private CampoResponseDTO campo;
    private List<UsuarioResponseDTO> entrenadores;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DeporteResponseDTO() {}

    public DeporteResponseDTO(UUID id, String nombre, String descripcion,
                              Boolean activo, CampoResponseDTO campo,
                              List<UsuarioResponseDTO> entrenadores,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.campo = campo;
        this.entrenadores = entrenadores;
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

    public CampoResponseDTO getCampo() { return campo; }
    public void setCampo(CampoResponseDTO campo) { this.campo = campo; }

    public List<UsuarioResponseDTO> getEntrenadores() { return entrenadores; }
    public void setEntrenadores(List<UsuarioResponseDTO> entrenadores) { this.entrenadores = entrenadores; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}