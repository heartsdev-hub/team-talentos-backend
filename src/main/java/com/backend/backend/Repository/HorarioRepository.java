package com.backend.backend.Repository;

import com.backend.backend.Entity.DiaSemana;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.Horario;
import com.backend.backend.Entity.TurnoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface HorarioRepository extends JpaRepository<Horario, UUID> {
    boolean existsByCampoIdAndDiaSemanaAndHoraInicioAndHoraFin(
            UUID campoId,
            DiaSemana diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin
    );

    List<Horario> findByCampoId(UUID campoId);

    @Query("""
            select count(h) > 0
            from Horario h
            where h.campo.id = :campoId
              and h.diaSemana = :diaSemana
              and h.horaInicio < :horaFin
              and h.horaFin > :horaInicio
            """)
    boolean existsHorarioCruzado(
            @Param("campoId") UUID campoId,
            @Param("diaSemana") DiaSemana diaSemana,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );

    @Query("""
            select distinct d
            from Horario h
            join h.campo c
            join c.deporte d
            where h.diaSemana = :diaSemana
              and h.turno = :turno
              and h.activo = true
              and d.activo = true
            order by d.nombre
            """)
    List<Deporte> findDeportesDisponiblesParaAsistencia(
            @Param("diaSemana") DiaSemana diaSemana,
            @Param("turno") TurnoHorario turno
    );

    @Query("""
            select h
            from Horario h
            join fetch h.campo c
            join fetch c.deporte d
            where d.id = :deporteId
              and h.diaSemana = :diaSemana
              and h.turno = :turno
              and h.activo = true
            order by h.horaInicio, h.horaFin
            """)
    List<Horario> findHorariosDisponiblesParaAsistencia(
            @Param("deporteId") UUID deporteId,
            @Param("diaSemana") DiaSemana diaSemana,
            @Param("turno") TurnoHorario turno
    );
}
