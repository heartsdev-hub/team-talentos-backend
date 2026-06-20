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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "MatriculaDetalle",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"matricula_id", "deporte_id", "horario_id", "DiaSemana"})
        }
)
public class MatriculaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deporte_id", nullable = false)
    private Deporte deporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "DiaSemana", nullable = false, length = 20)
    private DiaSemana diaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "Turno", nullable = false, length = 20)
    private TurnoMatriculaDetalle turno;

    @Column(name = "FechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "FechaFin")
    private LocalDate fechaFin;

    @Column(name = "TotalSesiones", nullable = false)
    private Integer totalSesiones = 12;

    @Column(name = "SesionesConsumidas", nullable = false)
    private Integer sesionesConsumidas = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", nullable = false, length = 20)
    private EstadoMatriculaDetalle estado = EstadoMatriculaDetalle.ACTIVO;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.fechaInicio == null) {
            this.fechaInicio = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = EstadoMatriculaDetalle.ACTIVO;
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

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }

    public Deporte getDeporte() { return deporte; }
    public void setDeporte(Deporte deporte) { this.deporte = deporte; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public TurnoMatriculaDetalle getTurno() { return turno; }
    public void setTurno(TurnoMatriculaDetalle turno) { this.turno = turno; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Integer getTotalSesiones() { return totalSesiones; }
    public void setTotalSesiones(Integer totalSesiones) { this.totalSesiones = totalSesiones; }

    public Integer getSesionesConsumidas() { return sesionesConsumidas; }
    public void setSesionesConsumidas(Integer sesionesConsumidas) { this.sesionesConsumidas = sesionesConsumidas; }

    public EstadoMatriculaDetalle getEstado() { return estado; }
    public void setEstado(EstadoMatriculaDetalle estado) { this.estado = estado; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
