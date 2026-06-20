package com.backend.backend.Controller;
import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.CampoDTO.CampoCreateDTO;
import com.backend.backend.DTO.CampoDTO.CampoResponseDTO;
import com.backend.backend.DTO.CampoDTO.CampoUpdateDTO;
import com.backend.backend.Service.CampoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/campos")
public class CampoController {

    private final CampoService campoService;

    public CampoController(CampoService campoService) {
        this.campoService = campoService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CampoResponseDTO>>> listarCampos() {
        return ResponseEntity.ok(
                ApiResponse.success("Campos listados correctamente", campoService.listarTodos())
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CampoResponseDTO>> crear(@Valid @RequestBody CampoCreateDTO campoCreateDTO) {
        CampoResponseDTO campoResponseDTO = campoService.crear(campoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Campo creado correctamente", campoResponseDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CampoResponseDTO>> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CampoUpdateDTO campoUpdateDTO) {
        CampoResponseDTO campoResponseDTO = campoService.actualizar(id, campoUpdateDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Campo actualizado correctamente", campoResponseDTO)
        );
    }
}