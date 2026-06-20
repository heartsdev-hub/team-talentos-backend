package com.backend.backend.DTO.UsuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
public class UsuarioUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El número de celular es obligatorio")
    private String numCelular;

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    private String password;

    private Set<UUID> rolesIds = new HashSet<>();

    private Set<UUID> deportesEntrenadosIds = new HashSet<>();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNumCelular() { return numCelular; }
    public void setNumCelular(String numCelular) { this.numCelular = numCelular; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<UUID> getRolesIds() { return rolesIds; }
    public void setRolesIds(Set<UUID> rolesIds) { this.rolesIds = rolesIds; }

    public Set<UUID> getDeportesEntrenadosIds() { return deportesEntrenadosIds; }
    public void setDeportesEntrenadosIds(Set<UUID> deportesEntrenadosIds) {
        this.deportesEntrenadosIds = deportesEntrenadosIds;
    }
}