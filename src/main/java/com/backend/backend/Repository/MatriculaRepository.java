package com.backend.backend.Repository;

import com.backend.backend.Entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MatriculaRepository extends JpaRepository<Matricula, UUID> {
    List<Matricula> findByUsuarioId(UUID usuarioId);
}
