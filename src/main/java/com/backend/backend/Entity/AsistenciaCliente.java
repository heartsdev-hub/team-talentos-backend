package com.backend.backend.Entity;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "AsistenciaCliente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"matricula_detalle_id", "FechaAsistencia"})
        }
)
public class AsistenciaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_detalle_id", nullable = false)
    private MatriculaDetalle matriculaDetalle;

    @Column(name = "FechaAsistencia", nullable = false)
    private LocalDate fechaAsistencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false, length = 20)
    private EstadoAsistencia estado;

    @Column(name = "Justificacion", columnDefinition = "TEXT")
    private String justificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_reprogramado_id")
    private Horario horarioReprogramado;

    @Column(name = "FechaReprogramada")
    private LocalDate fechaReprogramada;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.fechaAsistencia == null) {
            this.fechaAsistencia = LocalDate.now();
        }
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public MatriculaDetalle getMatriculaDetalle() { return matriculaDetalle; }
    public void setMatriculaDetalle(MatriculaDetalle matriculaDetalle) { this.matriculaDetalle = matriculaDetalle; }

    public LocalDate getFechaAsistencia() { return fechaAsistencia; }
    public void setFechaAsistencia(LocalDate fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public EstadoAsistencia getEstado() { return estado; }
    public void setEstado(EstadoAsistencia estado) { this.estado = estado; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public Horario getHorarioReprogramado() { return horarioReprogramado; }
    public void setHorarioReprogramado(Horario horarioReprogramado) { this.horarioReprogramado = horarioReprogramado; }

    public LocalDate getFechaReprogramada() { return fechaReprogramada; }
    public void setFechaReprogramada(LocalDate fechaReprogramada) { this.fechaReprogramada = fechaReprogramada; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
