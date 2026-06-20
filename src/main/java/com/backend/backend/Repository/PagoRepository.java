package com.backend.backend.Repository;

import com.backend.backend.Entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagoRepository extends JpaRepository<Pago, UUID> {
    boolean existsByMatriculaId(UUID matriculaId);
    Optional<Pago> findByMatriculaId(UUID matriculaId);
    List<Pago> findByMatriculaUsuarioId(UUID usuarioId);
}
