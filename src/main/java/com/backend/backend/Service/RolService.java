package com.backend.backend.Service;

import com.backend.backend.DTO.RolDTO.RolCreateDTO;
import com.backend.backend.DTO.RolDTO.RolResponseDTO;
import com.backend.backend.Entity.Rol;
import com.backend.backend.Exception.ResourceAlreadyExistsException;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.RolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    private final ModelMapper modelMapper;
    private final RolRepository rolRepository;

    public RolService(ModelMapper modelMapper, RolRepository rolRepository) {
        this.modelMapper = modelMapper;
        this.rolRepository = rolRepository;
    }
    public List<RolResponseDTO> listaRol(){
        List<Rol> roles = rolRepository.findAll();
        if(roles.isEmpty()){
            throw new ResourceNotFoundException("No hay roles registrados");
        }
        return roles.stream()
                .map(
                rol -> {
                    RolResponseDTO dto = new RolResponseDTO();
                    modelMapper.map(rol,dto);
                    return dto;
                }).toList();
    }
    public RolResponseDTO create(RolCreateDTO rolCreateDTO){
        String nombre = rolCreateDTO.getNombre().toUpperCase();
        if(rolRepository.existsByNombre(nombre)){
            throw new ResourceAlreadyExistsException("El nombre ya existe.");
        }
        rolCreateDTO.setNombre(nombre);

        Rol rol = new Rol();
        modelMapper.map(rolCreateDTO,rol);
        rol.setActivo(true);
        Rol rolSave = rolRepository.save(rol);
        RolResponseDTO rolResponseDTO = new RolResponseDTO();
        modelMapper.map(rolSave,rolResponseDTO);
        return rolResponseDTO;
    }
}
