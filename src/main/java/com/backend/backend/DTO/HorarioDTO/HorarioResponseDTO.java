package com.backend.backend.DTO.HorarioDTO;

import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.TurnoHorario;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class HorarioResponseDTO {

    private UUID id;
    private UUID campoId;
    private String campoNombre;
    private UUID deporteId;
    private String deporteNombre;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private TurnoHorario turno;
    private Integer cupoMaximo;
    private Long matriculadosActuales;
    private Integer cuposDisponibles;
    private Boolean activo;
    private List<HorarioEntrenadorResponseDTO> entrenadores;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public TurnoHorario getTurno() { return turno; }
    public void setTurno(TurnoHorario turno) { this.turno = turno; }

    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public Long getMatriculadosActuales() { return matriculadosActuales; }
    public void setMatriculadosActuales(Long matriculadosActuales) { this.matriculadosActuales = matriculadosActuales; }

    public Integer getCuposDisponibles() { return cuposDisponibles; }
    public void setCuposDisponibles(Integer cuposDisponibles) { this.cuposDisponibles = cuposDisponibles; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<HorarioEntrenadorResponseDTO> getEntrenadores() { return entrenadores; }
    public void setEntrenadores(List<HorarioEntrenadorResponseDTO> entrenadores) { this.entrenadores = entrenadores; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
