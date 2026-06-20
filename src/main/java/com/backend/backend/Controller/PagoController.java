package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.PagoDTO.PagoCreateDTO;
import com.backend.backend.DTO.PagoDTO.PagoDetalleCreateDTO;
import com.backend.backend.DTO.PagoDTO.PagoResponseDTO;
import com.backend.backend.Service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PagoResponseDTO>>> listarPagos() {
        return ResponseEntity.ok(
                ApiResponse.success("Pagos listados correctamente", pagoService.listarTodos())
        );
    }

    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<ApiResponse<PagoResponseDTO>> buscarPorMatricula(@PathVariable UUID matriculaId) {
        return ResponseEntity.ok(
                ApiResponse.success("Pago encontrado correctamente", pagoService.buscarPorMatricula(matriculaId))
        );
    }

    @GetMapping("/cliente/{usuarioId}/pendientes")
    public ResponseEntity<ApiResponse<List<PagoResponseDTO>>> listarPendientesPorCliente(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(
                ApiResponse.success("Pagos pendientes del cliente listados correctamente", pagoService.listarPendientesPorCliente(usuarioId))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PagoResponseDTO>> crear(@Valid @RequestBody PagoCreateDTO dto) {
        PagoResponseDTO responseDTO = pagoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Pago creado correctamente", responseDTO));
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<ApiResponse<PagoResponseDTO>> registrarPago(
            @PathVariable UUID id,
            @Valid @RequestBody PagoDetalleCreateDTO dto) {
        PagoResponseDTO responseDTO = pagoService.registrarPago(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Detalle de pago registrado correctamente", responseDTO));
    }
}
