package com.backend.backend.Config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseMigrationConfig {

    @Bean
    ApplicationRunner migrateHorarioDiaSemana(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS horario
                    ADD COLUMN IF NOT EXISTS dia_semana varchar(20)
                    """);
            jdbcTemplate.execute("""
                    UPDATE horario
                    SET dia_semana = 'LUNES'
                    WHERE dia_semana IS NULL
                    """);
            jdbcTemplate.execute("""
                    UPDATE horario
                    SET turno = 'MANANA'
                    WHERE turno IN ('MAÑANA', 'MAÃ‘ANA', 'MAÃ\u0091ANA')
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS horario
                    ALTER COLUMN dia_semana SET NOT NULL
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS horario
                    DROP CONSTRAINT IF EXISTS uk_horario_campo_hora
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS horario
                    DROP CONSTRAINT IF EXISTS uk_horario_campo_dia_hora
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS horario
                    ADD CONSTRAINT uk_horario_campo_dia_hora
                    UNIQUE (campo_id, dia_semana, hora_inicio, hora_fin)
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    DROP CONSTRAINT IF EXISTS ukdoyunywb89318bknqn1n4jk9o
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    DROP CONSTRAINT IF EXISTS UKdoyunywb89318bknqn1n4jk9o
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    ADD COLUMN IF NOT EXISTS dia_semana varchar(20)
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    ADD COLUMN IF NOT EXISTS turno varchar(20)
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    ALTER COLUMN dia_semana SET NOT NULL
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    ALTER COLUMN turno SET NOT NULL
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    DROP CONSTRAINT IF EXISTS uk_matricula_detalle_dia
                    """);
            jdbcTemplate.execute("""
                    ALTER TABLE IF EXISTS matricula_detalle
                    ADD CONSTRAINT uk_matricula_detalle_dia
                    UNIQUE (matricula_id, deporte_id, horario_id, dia_semana)
                    """);
        };
    }
}
