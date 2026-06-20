package com.backend.backend.Service;

import com.backend.backend.DTO.CampoDTO.CampoCreateDTO;
import com.backend.backend.DTO.CampoDTO.CampoResponseDTO;
import com.backend.backend.DTO.CampoDTO.CampoUpdateDTO;
import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.backend.backend.Entity.Campo;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Repository.CampoRepository;
import com.backend.backend.Repository.DeporteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CampoService {

    private final CampoRepository campoRepository;
    private final DeporteRepository deporteRepository;
    private final ModelMapper modelMapper;

    public CampoService(CampoRepository campoRepository,
                        DeporteRepository deporteRepository,
                        ModelMapper modelMapper) {
        this.campoRepository = campoRepository;
        this.deporteRepository = deporteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CampoResponseDTO crear(CampoCreateDTO dto) {
        Deporte deporte = deporteRepository.findById(dto.getDeporteId())
                .orElseThrow(() -> new RuntimeException(
                        "Deporte no encontrado con ID: " + dto.getDeporteId()));

        Campo campo = modelMapper.map(dto, Campo.class);
        campo.setDeporte(deporte);

        return toResponseDTO(campoRepository.save(campo));
    }

    @Transactional
    public CampoResponseDTO actualizar(UUID id, CampoUpdateDTO dto) {
        Campo campo = campoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Campo no encontrado con ID: " + id));

        Deporte deporte = deporteRepository.findById(dto.getDeporteId())
                .orElseThrow(() -> new RuntimeException(
                        "Deporte no encontrado con ID: " + dto.getDeporteId()));

        campo.setNombre(dto.getNombre());
        campo.setDescripcion(dto.getDescripcion());
        campo.setDeporte(deporte);

        return toResponseDTO(campoRepository.save(campo));
    }

    @Transactional(readOnly = true)
    public List<CampoResponseDTO> listarTodos() {
        return campoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    private CampoResponseDTO toResponseDTO(Campo campo) {
        CampoResponseDTO dto = modelMapper.map(campo, CampoResponseDTO.class);
        if (campo.getDeporte() != null) {
            dto.setDeporte(modelMapper.map(campo.getDeporte(), DeporteResponseDTO.class));
        }
        return dto;
    }
}