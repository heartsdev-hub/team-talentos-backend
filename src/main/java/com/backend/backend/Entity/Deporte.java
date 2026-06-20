package com.backend.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "Deporte",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"Nombre"}
                )
        }
)
public class Deporte {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Descripcion", nullable = false)
    private String descripcion;

    @Column(name = "Activo", nullable = false)
    private Boolean activo = true;

    @JsonIgnore
    @OneToOne(mappedBy = "deporte", fetch = FetchType.LAZY)
    private Campo campo;

    @JsonIgnore
    @ManyToMany(mappedBy = "deportesEntrenados")
    private Set<Usuario> entrenadores = new HashSet<>();

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.activo = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Deporte() {}

    public Deporte(UUID id, String nombre, String descripcion, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean isActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Campo getCampo() { return campo; }
    public void setCampo(Campo campo) { this.campo = campo; }

    public Set<Usuario> getEntrenadores() { return entrenadores; }
    public void setEntrenadores(Set<Usuario> entrenadores) { this.entrenadores = entrenadores; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}