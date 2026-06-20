package com.backend.backend.Mapper;

import com.backend.backend.DTO.CampoDTO.CampoCreateDTO;
import com.backend.backend.DTO.CampoDTO.CampoResponseDTO;
import com.backend.backend.DTO.CampoDTO.CampoUpdateDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteCreateDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioEntrenadorResponseDTO;
import com.backend.backend.DTO.HorarioDTO.HorarioResponseDTO;
import com.backend.backend.DTO.MatriculaDTO.MatriculaDetalleResponseDTO;
import com.backend.backend.DTO.MatriculaDTO.MatriculaResponseDTO;
import com.backend.backend.DTO.PagoDTO.PagoDetalleResponseDTO;
import com.backend.backend.DTO.PagoDTO.PagoResponseDTO;
import com.backend.backend.DTO.PrecioMatriculaDTO.PrecioMatriculaResponseDTO;
import com.backend.backend.DTO.RolDTO.RolCreateDTO;
import com.backend.backend.DTO.RolDTO.RolResponseDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioCreateDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.backend.backend.Entity.Campo;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.Horario;
import com.backend.backend.Entity.HorarioEntrenador;
import com.backend.backend.Entity.Matricula;
import com.backend.backend.Entity.MatriculaDetalle;
import com.backend.backend.Entity.Pago;
import com.backend.backend.Entity.PagoDetalle;
import com.backend.backend.Entity.PrecioMatricula;
import com.backend.backend.Entity.Rol;
import com.backend.backend.Entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper (){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        /**
         * mapper rol
         */
        modelMapper.createTypeMap(Rol.class, RolResponseDTO.class);
        modelMapper.createTypeMap(RolCreateDTO.class, Rol.class);
        /**
         * mapper deporte
         */
        modelMapper.emptyTypeMap(Deporte.class, DeporteResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(DeporteResponseDTO::setEntrenadores);
                    mapper.skip(DeporteResponseDTO::setCampo);
                })
                .implicitMappings();
        modelMapper.createTypeMap(DeporteCreateDTO.class, Deporte.class);

        /**
         * mapper campo
         */
        modelMapper.emptyTypeMap(Campo.class, CampoResponseDTO.class)
                .addMappings(mapper -> mapper.skip(CampoResponseDTO::setDeporte))
                .implicitMappings();
        modelMapper.createTypeMap(CampoCreateDTO.class, Campo.class);
        modelMapper.createTypeMap(CampoUpdateDTO.class, Campo.class);
        /**
         * mapper horario
         */
        modelMapper.emptyTypeMap(Horario.class, HorarioResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(HorarioResponseDTO::setCampoId);
                    mapper.skip(HorarioResponseDTO::setCampoNombre);
                    mapper.skip(HorarioResponseDTO::setDeporteId);
                    mapper.skip(HorarioResponseDTO::setDeporteNombre);
                    mapper.skip(HorarioResponseDTO::setMatriculadosActuales);
                    mapper.skip(HorarioResponseDTO::setCuposDisponibles);
                    mapper.skip(HorarioResponseDTO::setEntrenadores);
                })
                .implicitMappings();
        modelMapper.emptyTypeMap(HorarioEntrenador.class, HorarioEntrenadorResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(HorarioEntrenadorResponseDTO::setEntrenadorId);
                    mapper.skip(HorarioEntrenadorResponseDTO::setEntrenadorNombre);
                })
                .implicitMappings();
        /**
         * mapper matricula
         */
        modelMapper.emptyTypeMap(Matricula.class, MatriculaResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(MatriculaResponseDTO::setUsuarioId);
                    mapper.skip(MatriculaResponseDTO::setUsuarioNombre);
                    mapper.skip(MatriculaResponseDTO::setDetalles);
                })
                .implicitMappings();
        modelMapper.emptyTypeMap(MatriculaDetalle.class, MatriculaDetalleResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(MatriculaDetalleResponseDTO::setDeporteId);
                    mapper.skip(MatriculaDetalleResponseDTO::setDeporteNombre);
                    mapper.skip(MatriculaDetalleResponseDTO::setHorarioId);
                    mapper.skip(MatriculaDetalleResponseDTO::setCampoId);
                    mapper.skip(MatriculaDetalleResponseDTO::setCampoNombre);
                    mapper.skip(MatriculaDetalleResponseDTO::setDiaSemana);
                    mapper.skip(MatriculaDetalleResponseDTO::setHoraInicio);
                    mapper.skip(MatriculaDetalleResponseDTO::setHoraFin);
                    mapper.skip(MatriculaDetalleResponseDTO::setTurno);
                    mapper.skip(MatriculaDetalleResponseDTO::setSesionesRestantes);
                })
                .implicitMappings();
        /**
         * mapper pago
         */
        modelMapper.emptyTypeMap(Pago.class, PagoResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(PagoResponseDTO::setMatriculaId);
                    mapper.skip(PagoResponseDTO::setMora);
                    mapper.skip(PagoResponseDTO::setSaldoPendiente);
                    mapper.skip(PagoResponseDTO::setDetalles);
                })
                .implicitMappings();
        modelMapper.createTypeMap(PagoDetalle.class, PagoDetalleResponseDTO.class);
        modelMapper.createTypeMap(PrecioMatricula.class, PrecioMatriculaResponseDTO.class);
        /**
         * mapper usuario
         */
        modelMapper.emptyTypeMap(Usuario.class, UsuarioResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(UsuarioResponseDTO::setRoles);
                    mapper.skip(UsuarioResponseDTO::setDeportesEntrenados);
                })
                .implicitMappings();
        modelMapper.createTypeMap(UsuarioCreateDTO.class, Usuario.class);


        return modelMapper;
    }
}
