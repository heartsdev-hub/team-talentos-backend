package com.backend.backend.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "FechaMatricula", nullable = false)
    private LocalDate fechaMatricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false, length = 20)
    private EstadoMatricula estado = EstadoMatricula.ACTIVA;

    @Column(name = "TotalSesiones", nullable = false)
    private Integer totalSesiones = 12;

    @Column(name = "SesionesConsumidas", nullable = false)
    private Integer sesionesConsumidas = 0;

    @Column(name = "Observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatriculaDetalle> detalles = new ArrayList<>();

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.fechaMatricula == null) {
            this.fechaMatricula = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = EstadoMatricula.ACTIVA;
        }
        if (this.totalSesiones == null) {
            this.totalSesiones = 12;
        }
        if (this.sesionesConsumidas == null) {
            this.sesionesConsumidas = 0;
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addDetalle(MatriculaDetalle detalle) {
        detalles.add(detalle);
        detalle.setMatricula(this);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

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

    public List<MatriculaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<MatriculaDetalle> detalles) { this.detalles = detalles; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
