package com.backend.backend.Repository;

import com.backend.backend.Entity.PrecioMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PrecioMatriculaRepository extends JpaRepository<PrecioMatricula, UUID> {
    Optional<PrecioMatricula> findFirstByActivoTrueOrderByCreatedAtDesc();
}
