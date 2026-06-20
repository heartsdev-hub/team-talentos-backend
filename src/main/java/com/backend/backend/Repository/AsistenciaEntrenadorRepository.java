package com.backend.backend.Repository;

import com.backend.backend.Entity.AsistenciaEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface AsistenciaEntrenadorRepository extends JpaRepository<AsistenciaEntrenador, UUID> {
    boolean existsByHorarioEntrenadorIdAndFechaAsistencia(UUID horarioEntrenadorId, LocalDate fechaAsistencia);
}
