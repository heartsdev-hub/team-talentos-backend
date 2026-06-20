package com.backend.backend.DTO.PrecioMatriculaDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PrecioMatriculaCreateDTO {

    @NotNull(message = "El precio de 12 sesiones es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    private BigDecimal precioDoceSesiones;

    public BigDecimal getPrecioDoceSesiones() { return precioDoceSesiones; }
    public void setPrecioDoceSesiones(BigDecimal precioDoceSesiones) { this.precioDoceSesiones = precioDoceSesiones; }
}
