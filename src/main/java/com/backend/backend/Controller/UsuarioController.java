package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.UsuarioDTO.UsuarioCreateDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioUpdateDTO;
import com.backend.backend.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UsuarioResponseDTO>>> listarUsuarios() {
        return ResponseEntity.ok(
                ApiResponse.success("Usuarios listados correctamente", usuarioService.listarUsuarios())
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> crearUsuario(
            @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.createUser(usuarioCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario creado correctamente", usuarioResponseDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> actualizarUsuario(
            @PathVariable UUID id,
            @RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.actualizarUsuario(id, usuarioUpdateDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Usuario actualizado correctamente", usuarioResponseDTO)
        );
    }
}
