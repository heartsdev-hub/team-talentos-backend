package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.HorarioDTO.HorarioCreateDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioEntrenadorCreateDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioResponseDTO;
import com.backend.backend.Service.HorarioService;
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
@RequestMapping("/api/v1/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<HorarioResponseDTO>>> listarHorarios() {
        return ResponseEntity.ok(
                ApiResponse.success("Horarios listados correctamente", horarioService.listarTodos())
        );
    }

    @GetMapping("/campo/{campoId}")
    public ResponseEntity<ApiResponse<List<HorarioResponseDTO>>> listarPorCampo(@PathVariable UUID campoId) {
        return ResponseEntity.ok(
                ApiResponse.success("Horarios del campo listados correctamente", horarioService.listarPorCampo(campoId))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<HorarioResponseDTO>>> crear(@Valid @RequestBody HorarioCreateDTO dto) {
        List<HorarioResponseDTO> responseDTO = horarioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Horarios creados correctamente", responseDTO));
    }

    @PostMapping("/{id}/entrenadores")
    public ResponseEntity<ApiResponse<HorarioResponseDTO>> asignarEntrenador(
            @PathVariable UUID id,
            @Valid @RequestBody HorarioEntrenadorCreateDTO dto) {
        HorarioResponseDTO responseDTO = horarioService.asignarEntrenador(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Entrenador asignado correctamente", responseDTO));
    }
}
