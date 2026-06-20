package com.backend.backend.Service;

import com.backend.backend.DTO.PrecioMatriculaDTO.PrecioMatriculaCreateDTO;
import com.backend.backend.DTO.PrecioMatriculaDTO.PrecioMatriculaResponseDTO;
import com.backend.backend.Entity.PrecioMatricula;
import com.backend.backend.Repository.PrecioMatriculaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrecioMatriculaService {

    private final PrecioMatriculaRepository precioMatriculaRepository;
    private final ModelMapper modelMapper;

    public PrecioMatriculaService(PrecioMatriculaRepository precioMatriculaRepository, ModelMapper modelMapper) {
        this.precioMatriculaRepository = precioMatriculaRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public PrecioMatriculaResponseDTO crear(PrecioMatriculaCreateDTO dto) {
        precioMatriculaRepository.findFirstByActivoTrueOrderByCreatedAtDesc()
                .ifPresent(precioActual -> {
                    precioActual.setActivo(false);
                    precioMatriculaRepository.save(precioActual);
                });

        PrecioMatricula precio = new PrecioMatricula();
        precio.setPrecioDoceSesiones(dto.getPrecioDoceSesiones());
        precio.setActivo(true);
        return modelMapper.map(precioMatriculaRepository.save(precio), PrecioMatriculaResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public PrecioMatriculaResponseDTO obtenerActual() {
        return precioMatriculaRepository.findFirstByActivoTrueOrderByCreatedAtDesc()
                .map(precio -> modelMapper.map(precio, PrecioMatriculaResponseDTO.class))
                .orElse(null);
    }
}
