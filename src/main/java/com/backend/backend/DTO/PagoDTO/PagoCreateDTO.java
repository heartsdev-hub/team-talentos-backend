package com.backend.backend.DTO.PagoDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PagoCreateDTO {

    @NotNull(message = "El ID de la matricula es obligatorio")
    private UUID matriculaId;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.00", message = "El monto total no puede ser negativo")
    private BigDecimal montoTotal;

    private String metodoPreferido;
    private LocalDate fechaCancelacion;
    private String observaciones;

    public UUID getMatriculaId() { return matriculaId; }
    public void setMatriculaId(UUID matriculaId) { this.matriculaId = matriculaId; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public String getMetodoPreferido() { return metodoPreferido; }
    public void setMetodoPreferido(String metodoPreferido) { this.metodoPreferido = metodoPreferido; }

    public LocalDate getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(LocalDate fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
