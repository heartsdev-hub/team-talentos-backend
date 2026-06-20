package com.backend.backend.Service;

import com.backend.backend.DTO.CampoDTO.CampoResponseDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteCreateDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteUpdateDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.backend.backend.Entity.Campo;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.Usuario;
import com.backend.backend.Exception.ResourceAlreadyExistsException;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.DeporteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Service
public class DeporteService {
    private final DeporteRepository deporteRepository;
    private final ModelMapper modelMapper;

    public DeporteService(DeporteRepository deporteRepository, ModelMapper modelMapper) {
        this.deporteRepository = deporteRepository;
        this.modelMapper = modelMapper;
    }

    private CampoResponseDTO mapCampo(Campo campo) {
        if (campo == null) return null;
        CampoResponseDTO dto = new CampoResponseDTO();
        modelMapper.map(campo, dto);
        return dto;
    }

    private List<UsuarioResponseDTO> mapEntrenadores(Set<Usuario> entrenadores) {
        return entrenadores.stream()
                .map(usuario -> {
                    UsuarioResponseDTO dto = new UsuarioResponseDTO();
                    modelMapper.map(usuario, dto);
                    return dto;
                }).toList();
    }

    @Transactional(readOnly = true)
    public List<DeporteResponseDTO> listaDeporte() {
        List<Deporte> deportes = deporteRepository.findAll();
        if (deportes.isEmpty()) {
            throw new ResourceNotFoundException("No hay deportes registrados");
        }
        return deportes.stream()
                .map(deporte -> {
                    DeporteResponseDTO dto = new DeporteResponseDTO();
                    modelMapper.map(deporte, dto);
                    dto.setEntrenadores(mapEntrenadores(deporte.getEntrenadores()));
                    dto.setCampo(mapCampo(deporte.getCampo()));
                    return dto;
                }).toList();
    }

    public DeporteResponseDTO create(DeporteCreateDTO deporteCreateDTO) {
        String nombre = deporteCreateDTO.getNombre().toUpperCase();
        if (deporteRepository.existsByNombre(nombre)) {
            throw new ResourceAlreadyExistsException("El nombre ya existe.");
        }
        deporteCreateDTO.setNombre(nombre);

        Deporte deporte = new Deporte();
        modelMapper.map(deporteCreateDTO, deporte);
        deporte.setActivo(true);
        Deporte deporteSave = deporteRepository.save(deporte);

        DeporteResponseDTO deporteResponseDTO = new DeporteResponseDTO();
        modelMapper.map(deporteSave, deporteResponseDTO);
        deporteResponseDTO.setEntrenadores(new ArrayList<>());
        deporteResponseDTO.setCampo(null);
        return deporteResponseDTO;
    }

    public DeporteResponseDTO actualizar(UUID id, DeporteUpdateDTO dto) {
        Deporte deporte = deporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deporte no encontrado"));

        String nombre = dto.getNombre().toUpperCase();
        if (deporteRepository.existsByNombre(nombre) && !deporte.getNombre().equals(nombre)) {
            throw new ResourceAlreadyExistsException("El nombre ya existe.");
        }

        deporte.setNombre(nombre);
        deporte.setDescripcion(dto.getDescripcion());

        Deporte deporteActualizado = deporteRepository.save(deporte);

        DeporteResponseDTO deporteResponseDTO = new DeporteResponseDTO();
        modelMapper.map(deporteActualizado, deporteResponseDTO);
        deporteResponseDTO.setEntrenadores(mapEntrenadores(deporteActualizado.getEntrenadores()));
        deporteResponseDTO.setCampo(mapCampo(deporteActualizado.getCampo()));
        return deporteResponseDTO;
    }
}