package com.backend.backend.Repository;

import com.backend.backend.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
