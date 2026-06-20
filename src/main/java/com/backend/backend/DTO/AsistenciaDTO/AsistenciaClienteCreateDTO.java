package com.backend.backend.DTO.AsistenciaDTO;

import com.backend.backend.Entity.EstadoAsistencia;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class AsistenciaClienteCreateDTO {

    @NotNull(message = "El ID del detalle de matricula es obligatorio")
    private UUID matriculaDetalleId;

    private LocalDate fechaAsistencia;

    @NotNull(message = "El estado de asistencia es obligatorio")
    private EstadoAsistencia estado;

    public UUID getMatriculaDetalleId() { return matriculaDetalleId; }
    public void setMatriculaDetalleId(UUID matriculaDetalleId) { this.matriculaDetalleId = matriculaDetalleId; }

    public LocalDate getFechaAsistencia() { return fechaAsistencia; }
    public void setFechaAsistencia(LocalDate fechaAsistencia) { this.fechaAsistencia = fechaAsistencia; }

    public EstadoAsistencia getEstado() { return estado; }
    public void setEstado(EstadoAsistencia estado) { this.estado = estado; }
}
