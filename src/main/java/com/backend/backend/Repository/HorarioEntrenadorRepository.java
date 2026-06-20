package com.backend.backend.Repository;

import com.backend.backend.Entity.HorarioEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface HorarioEntrenadorRepository extends JpaRepository<HorarioEntrenador, UUID> {
    boolean existsByHorarioIdAndEntrenadorIdAndFechaInicio(UUID horarioId, UUID entrenadorId, LocalDate fechaInicio);
}
