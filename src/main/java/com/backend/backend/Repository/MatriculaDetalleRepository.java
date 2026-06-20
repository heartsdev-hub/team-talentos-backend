package com.backend.backend.Repository;

import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.EstadoMatriculaDetalle;
import com.backend.backend.Entity.MatriculaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MatriculaDetalleRepository extends JpaRepository<MatriculaDetalle, UUID> {
    long countByHorarioIdAndEstado(UUID horarioId, EstadoMatriculaDetalle estado);
    long countByHorarioIdAndDiaSemanaAndEstado(UUID horarioId, DiaSemana diaSemana, EstadoMatriculaDetalle estado);
    List<MatriculaDetalle> findByMatriculaIdAndDeporteId(UUID matriculaId, UUID deporteId);
    boolean existsByMatriculaUsuarioIdAndDeporteIdAndEstado(UUID usuarioId, UUID deporteId, EstadoMatriculaDetalle estado);

    @Query("""
            select md
            from MatriculaDetalle md
            join fetch md.matricula m
            join fetch m.usuario u
            join fetch md.deporte d
            join fetch md.horario h
            where h.id = :horarioId
              and md.diaSemana = :diaSemana
              and md.estado = :estado
            order by u.apellido, u.nombre
            """)
    List<MatriculaDetalle> findAlumnosParaAsistencia(
            @Param("horarioId") UUID horarioId,
            @Param("diaSemana") DiaSemana diaSemana,
            @Param("estado") EstadoMatriculaDetalle estado
    );
}
