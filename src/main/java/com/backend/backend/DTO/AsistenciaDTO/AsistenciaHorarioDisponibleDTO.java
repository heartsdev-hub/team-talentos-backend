package com.backend.backend.DTO.AsistenciaDTO;

import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.TurnoHorario;

import java.time.LocalTime;
import java.util.UUID;

public class AsistenciaHorarioDisponibleDTO {

    private UUID horarioId;
    private UUID campoId;
    private String campoNombre;
    private UUID deporteId;
    private String deporteNombre;
    private DiaSemana diaSemana;
    private TurnoHorario turno;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer cupoMaximo;
    private Long matriculados;

    public UUID getHorarioId() { return horarioId; }
    public void setHorarioId(UUID horarioId) { this.horarioId = horarioId; }

    public UUID getCampoId() { return campoId; }
    public void setCampoId(UUID campoId) { this.campoId = campoId; }

    public String getCampoNombre() { return campoNombre; }
    public void setCampoNombre(String campoNombre) { this.campoNombre = campoNombre; }

    public UUID getDeporteId() { return deporteId; }
    public void setDeporteId(UUID deporteId) { this.deporteId = deporteId; }

    public String getDeporteNombre() { return deporteNombre; }
    public void setDeporteNombre(String deporteNombre) { this.deporteNombre = deporteNombre; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public TurnoHorario getTurno() { return turno; }
    public void setTurno(TurnoHorario turno) { this.turno = turno; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public Long getMatriculados() { return matriculados; }
    public void setMatriculados(Long matriculados) { this.matriculados = matriculados; }
}
