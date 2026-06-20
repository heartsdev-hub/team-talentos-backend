package com.backend.backend.Repository;

import com.backend.backend.Entity.PagoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagoDetalleRepository extends JpaRepository<PagoDetalle, UUID> {
}
