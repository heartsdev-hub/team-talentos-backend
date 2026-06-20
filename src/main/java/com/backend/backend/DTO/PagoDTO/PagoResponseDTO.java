package com.backend.backend.DTO.PagoDTO;

import com.backend.backend.Entity.EstadoPago;
import com.backend.backend.Entity.TipoPago;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PagoResponseDTO {

    private UUID id;
    private UUID matriculaId;
    private BigDecimal montoTotal;
    private BigDecimal montoPagado;
    private BigDecimal mora;
    private BigDecimal saldoPendiente;
    private EstadoPago estado;
    private TipoPago tipoPago;
    private String metodoPreferido;
    private LocalDate fechaCancelacion;
    private String observaciones;
    private List<PagoDetalleResponseDTO> detalles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getMatriculaId() { return matriculaId; }
    public void setMatriculaId(UUID matriculaId) { this.matriculaId = matriculaId; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }

    public BigDecimal getMora() { return mora; }
    public void setMora(BigDecimal mora) { this.mora = mora; }

    public BigDecimal getSaldoPendiente() { return saldoPendiente; }
    public void setSaldoPendiente(BigDecimal saldoPendiente) { this.saldoPendiente = saldoPendiente; }

    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }

    public TipoPago getTipoPago() { return tipoPago; }
    public void setTipoPago(TipoPago tipoPago) { this.tipoPago = tipoPago; }

    public String getMetodoPreferido() { return metodoPreferido; }
    public void setMetodoPreferido(String metodoPreferido) { this.metodoPreferido = metodoPreferido; }

    public LocalDate getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(LocalDate fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<PagoDetalleResponseDTO> getDetalles() { return detalles; }
    public void setDetalles(List<PagoDetalleResponseDTO> detalles) { this.detalles = detalles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
