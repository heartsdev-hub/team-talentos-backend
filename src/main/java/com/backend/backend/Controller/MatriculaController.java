package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.MatriculaDTO.MatriculaCreateDTO;
import com.backend.backend.DTO.MatriculaDTO.MatriculaResponseDTO;
import com.backend.backend.Service.MatriculaService;
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
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<MatriculaResponseDTO>>> listarMatriculas() {
        return ResponseEntity.ok(
                ApiResponse.success("Matriculas listadas correctamente", matriculaService.listarTodos())
        );
    }

    @GetMapping("/cliente/{usuarioId}")
    public ResponseEntity<ApiResponse<List<MatriculaResponseDTO>>> listarPorCliente(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(
                ApiResponse.success("Matriculas del cliente listadas correctamente", matriculaService.listarPorCliente(usuarioId))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MatriculaResponseDTO>> crear(@Valid @RequestBody MatriculaCreateDTO dto) {
        MatriculaResponseDTO responseDTO = matriculaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Matricula creada correctamente", responseDTO));
    }
}
