package com.backend.backend.Repository;

import com.backend.backend.Entity.Campo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampoRepository extends JpaRepository<Campo, UUID> {
}
