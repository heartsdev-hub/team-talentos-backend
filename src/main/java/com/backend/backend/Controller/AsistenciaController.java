package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaAlumnoDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaClienteContextoDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaClienteCreateDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaClienteJustificarDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaDeporteDisponibleDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaEntrenadorCreateDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaHorarioDisponibleDTO;
import com.backend.backend.DTO.AsistenciaDTO.AsistenciaResponseDTO;
import com.backend.backend.Service.AsistenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/asistencias")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    @GetMapping("/clientes/contexto")
    public ResponseEntity<ApiResponse<AsistenciaClienteContextoDTO>> obtenerContextoCliente() {
        return ResponseEntity.ok(
                ApiResponse.success("Contexto de asistencia obtenido correctamente", asistenciaService.obtenerContextoCliente())
        );
    }

    @GetMapping("/clientes/deportes")
    public ResponseEntity<ApiResponse<List<AsistenciaDeporteDisponibleDTO>>> listarDeportesCliente(
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) String turno) {
        return ResponseEntity.ok(
                ApiResponse.success("Deportes disponibles para asistencia listados correctamente", asistenciaService.listarDeportesCliente(fecha, turno))
        );
    }

    @GetMapping("/clientes/deportes/{deporteId}/horarios")
    public ResponseEntity<ApiResponse<List<AsistenciaHorarioDisponibleDTO>>> listarHorariosCliente(
            @PathVariable UUID deporteId,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) String turno) {
        return ResponseEntity.ok(
                ApiResponse.success("Horarios disponibles para asistencia listados correctamente", asistenciaService.listarHorariosCliente(deporteId, fecha, turno))
        );
    }

    @GetMapping("/clientes/horarios/{horarioId}/alumnos")
    public ResponseEntity<ApiResponse<List<AsistenciaAlumnoDTO>>> listarAlumnosCliente(
            @PathVariable UUID horarioId,
            @RequestParam(required = false) LocalDate fecha) {
        return ResponseEntity.ok(
                ApiResponse.success("Alumnos matriculados listados correctamente", asistenciaService.listarAlumnosCliente(horarioId, fecha))
        );
    }

    @PostMapping("/clientes")
    public ResponseEntity<ApiResponse<AsistenciaResponseDTO>> registrarCliente(@Valid @RequestBody AsistenciaClienteCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asistencia de cliente registrada correctamente", asistenciaService.registrarCliente(dto)));
    }

    @PutMapping("/clientes/{id}/justificar")
    public ResponseEntity<ApiResponse<AsistenciaResponseDTO>> justificarCliente(
            @PathVariable UUID id,
            @Valid @RequestBody AsistenciaClienteJustificarDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.success("Falta justificada correctamente", asistenciaService.justificarCliente(id, dto))
        );
    }

    @PostMapping("/entrenadores")
    public ResponseEntity<ApiResponse<AsistenciaResponseDTO>> registrarEntrenador(@Valid @RequestBody AsistenciaEntrenadorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asistencia de entrenador registrada correctamente", asistenciaService.registrarEntrenador(dto)));
    }
}
