package com.backend.backend.Repository;

import com.backend.backend.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RolRepository extends JpaRepository<Rol, UUID> {
    boolean existsByNombre(String nombre);

    Optional<Rol> findByNombre(String nombre);
}
