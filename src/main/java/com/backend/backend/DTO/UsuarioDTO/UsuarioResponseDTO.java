package com.backend.backend.DTO.UsuarioDTO;

import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.backend.backend.DTO.RolDTO.RolResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class UsuarioResponseDTO {

    private UUID id;
    private String nombre;
    private String apellido;
    private String dni;
    private String numCelular;
    private String email;
    private Boolean activo;
    private List<RolResponseDTO> roles;
    private List<DeporteResponseDTO> deportesEntrenados;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(UUID id, String nombre, String apellido, String dni,
                              String numCelular, String email, Boolean activo,
                              LocalDateTime createdAt, LocalDateTime updatedAt,
                              List<RolResponseDTO> roles, List<DeporteResponseDTO> deportesEntrenados) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.numCelular = numCelular;
        this.email = email;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.deportesEntrenados = deportesEntrenados;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNumCelular() { return numCelular; }
    public void setNumCelular(String numCelular) { this.numCelular = numCelular; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<RolResponseDTO> getRoles() { return roles; }
    public void setRoles(List<RolResponseDTO> roles) { this.roles = roles; }

    public List<DeporteResponseDTO> getDeportesEntrenados() { return deportesEntrenados; }
    public void setDeportesEntrenados(List<DeporteResponseDTO> deportesEntrenados) { this.deportesEntrenados = deportesEntrenados; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}