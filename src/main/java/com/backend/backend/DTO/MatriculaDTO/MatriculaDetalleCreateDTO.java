package com.backend.backend.DTO.MatriculaDTO;

import com.backend.backend.Entity.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class MatriculaDetalleCreateDTO {

    @NotNull(message = "El ID del deporte es obligatorio")
    private UUID deporteId;

    @NotNull(message = "El ID del horario es obligatorio")
    private UUID horarioId;

    @NotNull(message = "El dia de semana es obligatorio")
    private DiaSemana diaSemana;

    @NotBlank(message = "El turno es obligatorio")
    private String turno;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public UUID getDeporteId() { return deporteId; }
    public void setDeporteId(UUID deporteId) { this.deporteId = deporteId; }

    public UUID getHorarioId() { return horarioId; }
    public void setHorarioId(UUID horarioId) { this.horarioId = horarioId; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}
