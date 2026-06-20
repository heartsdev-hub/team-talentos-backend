package com.backend.backend.DTO.MatriculaDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MatriculaCreateDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;

    private LocalDate fechaMatricula;
    private String observaciones;

    @Valid
    @NotEmpty(message = "Debe enviar al menos un detalle de matricula")
    private List<MatriculaDetalleCreateDTO> detalles;

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<MatriculaDetalleCreateDTO> getDetalles() { return detalles; }
    public void setDetalles(List<MatriculaDetalleCreateDTO> detalles) { this.detalles = detalles; }
}
