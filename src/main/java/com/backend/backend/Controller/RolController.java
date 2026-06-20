package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.RolDTO.RolCreateDTO;
import com.backend.backend.DTO.RolDTO.RolResponseDTO;
import com.backend.backend.Service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RolResponseDTO>>> listRol(){
        return ResponseEntity.ok(
                ApiResponse.success("Roles listados correctamente", rolService.listaRol())
        );
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RolResponseDTO>> create(@RequestBody RolCreateDTO rolCreateDTO){
        RolResponseDTO rolResponseDTO = rolService.create(rolCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Rol creado correctamente",rolResponseDTO));
    }
}
