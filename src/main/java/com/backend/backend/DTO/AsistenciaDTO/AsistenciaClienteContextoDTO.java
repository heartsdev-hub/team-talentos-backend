package com.backend.backend.DTO.AsistenciaDTO;

import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.TurnoHorario;

import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaClienteContextoDTO {

    private LocalDate fecha;
    private LocalTime horaActual;
    private DiaSemana diaSemana;
    private TurnoHorario turnoActual;

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraActual() { return horaActual; }
    public void setHoraActual(LocalTime horaActual) { this.horaActual = horaActual; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public TurnoHorario getTurnoActual() { return turnoActual; }
    public void setTurnoActual(TurnoHorario turnoActual) { this.turnoActual = turnoActual; }
}
