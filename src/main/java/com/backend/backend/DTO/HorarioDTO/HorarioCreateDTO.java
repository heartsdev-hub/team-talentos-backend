package com.backend.backend.DTO.HorarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;
import java.util.UUID;

public class HorarioCreateDTO {

    @NotNull(message = "El ID del campo es obligatorio")
    private UUID campoId;

    @NotNull(message = "El ID del entrenador es obligatorio")
    private UUID entrenadorId;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotBlank(message = "El turno es obligatorio")
    private String turno;

    @NotNull(message = "El cupo maximo es obligatorio")
    @Positive(message = "El cupo maximo debe ser mayor que cero")
    private Integer cupoMaximo;

    public UUID getCampoId() { return campoId; }
    public void setCampoId(UUID campoId) { this.campoId = campoId; }

    public UUID getEntrenadorId() { return entrenadorId; }
    public void setEntrenadorId(UUID entrenadorId) { this.entrenadorId = entrenadorId; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }
}
