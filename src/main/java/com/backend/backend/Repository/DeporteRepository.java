package com.backend.backend.Repository;

import com.backend.backend.Entity.Deporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeporteRepository extends JpaRepository<Deporte, UUID> {
    boolean existsByNombre(String nombre);
}
