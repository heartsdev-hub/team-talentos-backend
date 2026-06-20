package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.DeporteDTO.DeporteCreateDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.backend.backend.Service.DeporteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deportes")
public class DeporteController {

    private final DeporteService deporteService;

    public DeporteController(DeporteService deporteService) {
        this.deporteService = deporteService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<DeporteResponseDTO>>> listDeporte() {
        return ResponseEntity.ok(
                ApiResponse.success("Deportes listados correctamente", deporteService.listaDeporte())
        );
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DeporteResponseDTO>> create(@RequestBody DeporteCreateDTO deporteCreateDTO) {
        DeporteResponseDTO deporteResponseDTO = deporteService.create(deporteCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Deporte creado correctamente", deporteResponseDTO));
    }
}
