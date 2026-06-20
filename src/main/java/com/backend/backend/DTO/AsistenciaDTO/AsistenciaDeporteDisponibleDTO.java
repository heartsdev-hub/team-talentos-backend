package com.backend.backend.DTO.AsistenciaDTO;

import java.util.UUID;

public class AsistenciaDeporteDisponibleDTO {

    private UUID deporteId;
    private String deporteNombre;

    public UUID getDeporteId() { return deporteId; }
    public void setDeporteId(UUID deporteId) { this.deporteId = deporteId; }

    public String getDeporteNombre() { return deporteNombre; }
    public void setDeporteNombre(String deporteNombre) { this.deporteNombre = deporteNombre; }
}
