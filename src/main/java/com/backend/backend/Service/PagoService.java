package com.backend.backend.Service;

import com.backend.backend.DTO.PagoDTO.PagoCreateDTO;
import com.backend.backend.DTO.PagoDTO.PagoDetalleCreateDTO;
import com.backend.backend.DTO.PagoDTO.PagoDetalleResponseDTO;
import com.backend.backend.DTO.PagoDTO.PagoResponseDTO;
import com.backend.backend.Entity.EstadoPago;
import com.backend.backend.Entity.Matricula;
import com.backend.backend.Entity.Pago;
import com.backend.backend.Entity.PagoDetalle;
import com.backend.backend.Entity.TipoPago;
import com.backend.backend.Exception.ResourceAlreadyExistsException;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.MatriculaRepository;
import com.backend.backend.Repository.PagoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final MatriculaRepository matriculaRepository;
    private final ModelMapper modelMapper;

    public PagoService(PagoRepository pagoRepository,
                       MatriculaRepository matriculaRepository,
                       ModelMapper modelMapper) {
        this.pagoRepository = pagoRepository;
        this.matriculaRepository = matriculaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public PagoResponseDTO crear(PagoCreateDTO dto) {
        if (pagoRepository.existsByMatriculaId(dto.getMatriculaId())) {
            throw new ResourceAlreadyExistsException("La matricula ya tiene un pago registrado");
        }

        Matricula matricula = matriculaRepository.findById(dto.getMatriculaId())
                .orElseThrow(() -> new ResourceNotFoundException("Matricula no encontrada con ID: " + dto.getMatriculaId()));

        Pago pago = new Pago();
        pago.setMatricula(matricula);
        pago.setMontoTotal(dto.getMontoTotal());
        pago.setMontoPagado(BigDecimal.ZERO);
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setMetodoPreferido(dto.getMetodoPreferido());
        pago.setFechaCancelacion(dto.getFechaCancelacion());
        pago.setObservaciones(dto.getObservaciones());

        return toResponseDTO(pagoRepository.save(pago));
    }

    @Transactional
    public PagoResponseDTO registrarPago(UUID pagoId, PagoDetalleCreateDTO dto) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + pagoId));

        if (pago.getDetalles().size() >= 2) {
            throw new IllegalArgumentException("El pago solo puede dividirse en maximo 2 partes");
        }

        BigDecimal nuevoMontoPagado = pago.getMontoPagado().add(dto.getMonto());
        BigDecimal totalConMora = calcularTotalConMora(pago);
        validarMontoNoExcedeTotal(nuevoMontoPagado, totalConMora);
        validarTipoPago(pago, dto, nuevoMontoPagado);

        PagoDetalle detalle = new PagoDetalle();
        detalle.setPago(pago);
        detalle.setFechaPago(dto.getFechaPago() != null ? dto.getFechaPago() : LocalDateTime.now());
        detalle.setMonto(dto.getMonto());
        detalle.setMetodoPago(dto.getMetodoPago());
        detalle.setReferencia(dto.getReferencia());
        detalle.setObservaciones(dto.getObservaciones());

        pago.addDetalle(detalle);
        pago.setMontoPagado(nuevoMontoPagado);
        actualizarFechaCancelacion(pago, dto, nuevoMontoPagado);
        actualizarEstado(pago);

        return toResponseDTO(pagoRepository.save(pago));
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarTodos() {
        return pagoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO buscarPorMatricula(UUID matriculaId) {
        Pago pago = pagoRepository.findByMatriculaId(matriculaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado para la matricula: " + matriculaId));
        return toResponseDTO(pago);
    }

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> listarPendientesPorCliente(UUID usuarioId) {
        return pagoRepository.findByMatriculaUsuarioId(usuarioId)
                .stream()
                .filter(pago -> pago.getEstado() == EstadoPago.PENDIENTE)
                .map(this::toResponseDTO)
                .toList();
    }

    private void actualizarEstado(Pago pago) {
        pago.setEstado(pago.getMontoPagado().compareTo(calcularTotalConMora(pago)) >= 0
                ? EstadoPago.CANCELADO
                : EstadoPago.PENDIENTE);
    }

    private void validarTipoPago(Pago pago, PagoDetalleCreateDTO dto, BigDecimal nuevoMontoPagado) {
        if (pago.getDetalles().isEmpty()) {
            if (dto.getTipoPago() == null) {
                throw new IllegalArgumentException("Debe indicar si el pago es AL_CONTADO o DOS_PARTES");
            }
            pago.setTipoPago(dto.getTipoPago());
        }

        if (pago.getTipoPago() == TipoPago.AL_CONTADO) {
            if (!pago.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("El pago al contado solo permite un registro");
            }
            if (dto.getMonto().compareTo(pago.getMontoTotal()) != 0) {
                throw new IllegalArgumentException("El pago al contado debe ser por el monto total");
            }
            return;
        }

        BigDecimal mitad = pago.getMontoTotal().divide(BigDecimal.valueOf(2));
        if (pago.getDetalles().isEmpty()) {
            if (dto.getMonto().compareTo(mitad) != 0) {
                throw new IllegalArgumentException("La primera parte debe ser la mitad del total");
            }
            validarFechaCancelacion(dto);
        } else {
            BigDecimal saldoPendiente = calcularSaldoPendiente(pago);
            if (dto.getMonto().compareTo(saldoPendiente) != 0) {
                throw new IllegalArgumentException("La segunda parte debe cancelar el saldo pendiente");
            }
            if (nuevoMontoPagado.compareTo(calcularTotalConMora(pago)) != 0) {
                throw new IllegalArgumentException("El segundo pago debe cancelar el total");
            }
        }
    }

    private void validarFechaCancelacion(PagoDetalleCreateDTO dto) {
        if (dto.getFechaCancelacion() == null) {
            throw new IllegalArgumentException("Debe indicar la fecha de cancelacion para el pago en dos partes");
        }

        LocalDate fechaPago = dto.getFechaPago() != null ? dto.getFechaPago().toLocalDate() : LocalDate.now();
        if (dto.getFechaCancelacion().isAfter(fechaPago.plusDays(15))) {
            throw new IllegalArgumentException("La fecha de cancelacion no puede superar los 15 dias");
        }
    }

    private void actualizarFechaCancelacion(Pago pago, PagoDetalleCreateDTO dto, BigDecimal nuevoMontoPagado) {
        if (pago.getTipoPago() == TipoPago.DOS_PARTES && nuevoMontoPagado.compareTo(calcularTotalConMora(pago)) < 0) {
            pago.setFechaCancelacion(dto.getFechaCancelacion());
            return;
        }
        pago.setFechaCancelacion(null);
    }

    private PagoResponseDTO toResponseDTO(Pago pago) {
        PagoResponseDTO dto = modelMapper.map(pago, PagoResponseDTO.class);
        dto.setMatriculaId(pago.getMatricula().getId());
        dto.setMora(calcularMora(pago));
        dto.setSaldoPendiente(calcularSaldoPendiente(pago));
        dto.setDetalles(pago.getDetalles()
                .stream()
                .map(this::toDetalleResponseDTO)
                .toList());
        return dto;
    }

    private void validarMontoNoExcedeTotal(BigDecimal nuevoMontoPagado, BigDecimal totalConMora) {
        if (nuevoMontoPagado.compareTo(totalConMora) > 0) {
            throw new IllegalArgumentException("El monto pagado no puede superar el total pendiente");
        }
    }

    private BigDecimal calcularSaldoPendiente(Pago pago) {
        BigDecimal saldo = calcularTotalConMora(pago).subtract(pago.getMontoPagado());
        return saldo.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : saldo;
    }

    private BigDecimal calcularTotalConMora(Pago pago) {
        return pago.getMontoTotal().add(calcularMora(pago));
    }

    private BigDecimal calcularMora(Pago pago) {
        if (pago.getEstado() == EstadoPago.CANCELADO
                || pago.getTipoPago() != TipoPago.DOS_PARTES
                || pago.getFechaCancelacion() == null
                || !LocalDate.now().isAfter(pago.getFechaCancelacion())) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        long diasVencidos = ChronoUnit.DAYS.between(pago.getFechaCancelacion(), LocalDate.now());
        return BigDecimal.valueOf(diasVencidos).setScale(2, RoundingMode.HALF_UP);
    }

    private PagoDetalleResponseDTO toDetalleResponseDTO(PagoDetalle detalle) {
        return modelMapper.map(detalle, PagoDetalleResponseDTO.class);
    }
}
