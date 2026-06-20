package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.PrecioMatriculaDTO.PrecioMatriculaCreateDTO;
import com.backend.backend.DTO.PrecioMatriculaDTO.PrecioMatriculaResponseDTO;
import com.backend.backend.Service.PrecioMatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/precios-matricula")
public class PrecioMatriculaController {

    private final PrecioMatriculaService precioMatriculaService;

    public PrecioMatriculaController(PrecioMatriculaService precioMatriculaService) {
        this.precioMatriculaService = precioMatriculaService;
    }

    @GetMapping("/actual")
    public ResponseEntity<ApiResponse<PrecioMatriculaResponseDTO>> obtenerActual() {
        return ResponseEntity.ok(
                ApiResponse.success("Precio actual obtenido correctamente", precioMatriculaService.obtenerActual())
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PrecioMatriculaResponseDTO>> crear(@Valid @RequestBody PrecioMatriculaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Precio de matricula creado correctamente", precioMatriculaService.crear(dto)));
    }
}
