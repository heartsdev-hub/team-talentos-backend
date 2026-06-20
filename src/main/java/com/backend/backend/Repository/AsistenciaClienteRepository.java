package com.backend.backend.Repository;

import com.backend.backend.Entity.AsistenciaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface AsistenciaClienteRepository extends JpaRepository<AsistenciaCliente, UUID> {
    boolean existsByMatriculaDetalleIdAndFechaAsistencia(UUID matriculaDetalleId, LocalDate fechaAsistencia);
    Optional<AsistenciaCliente> findByMatriculaDetalleIdAndFechaAsistencia(UUID matriculaDetalleId, LocalDate fechaAsistencia);
}
