package com.backend.backend.Controller;

import com.backend.backend.DTO.ApiResponse;
import com.backend.backend.DTO.Auth.LoginRequestDTO;
import com.backend.backend.DTO.Auth.LoginResponseDTO;
import com.backend.backend.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        LoginResponseDTO response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("Login exitoso", response)
        );
    }
}