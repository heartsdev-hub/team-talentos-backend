package com.backend.backend.Service;

import com.backend.backend.DTO.DeporteDTO.DeporteResponseDTO;
import com.backend.backend.DTO.RolDTO.RolResponseDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioCreateDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.backend.backend.DTO.UsuarioDTO.UsuarioUpdateDTO;
import com.backend.backend.Entity.Deporte;
import com.backend.backend.Entity.Rol;
import com.backend.backend.Entity.Usuario;
import com.backend.backend.Exception.ResourceAlreadyExistsException;
import com.backend.backend.Exception.ResourceNotFoundException;
import com.backend.backend.Repository.DeporteRepository;
import com.backend.backend.Repository.RolRepository;
import com.backend.backend.Repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final DeporteRepository deporteRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, DeporteRepository deporteRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.deporteRepository = deporteRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("No hay usuarios registrados");
        }
        return usuarios.stream()
                .map(usuario -> {
                    UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
                    modelMapper.map(usuario, responseDTO);

                    List<RolResponseDTO> roles = usuario.getRoles()
                            .stream()
                            .map(rol -> {
                                RolResponseDTO rolDTO = new RolResponseDTO();
                                modelMapper.map(rol, rolDTO);
                                return rolDTO;
                            }).toList();
                    responseDTO.setRoles(roles);

                    List<DeporteResponseDTO> deportes = usuario.getDeportesEntrenados()
                            .stream()
                            .map(deporte -> {
                                DeporteResponseDTO deporteDTO = new DeporteResponseDTO();
                                modelMapper.map(deporte, deporteDTO);
                                deporteDTO.setEntrenadores(new ArrayList<>());
                                return deporteDTO;
                            }).toList();
                    responseDTO.setDeportesEntrenados(deportes);

                    return responseDTO;
                }).toList();
    }
    @Transactional
    public UsuarioResponseDTO createUser(UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioRepository.existsByDni(usuarioCreateDTO.getDni())) {
            throw new ResourceAlreadyExistsException("Ya existe un usuario con ese DNI");
        }
        if (usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Ya existe un usuario con ese email");
        }

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(usuarioCreateDTO.getRolesIds()));
        if (roles.size() != usuarioCreateDTO.getRolesIds().size()) {
            throw new ResourceNotFoundException("Uno o más roles no existen");
        }

        Usuario usuario = new Usuario();
        modelMapper.map(usuarioCreateDTO, usuario);
        usuario.setPassword(passwordEncoder.encode(usuarioCreateDTO.getPassword()));
        usuario.setRoles(roles);

        boolean esEntrenador = tieneRolEntrenador(roles);
        if (esEntrenador) {
            Set<Deporte> deportes = new HashSet<>(
                    deporteRepository.findAllById(usuarioCreateDTO.getDeportesEntrenadosIds())
            );
            if (deportes.size() != usuarioCreateDTO.getDeportesEntrenadosIds().size()) {
                throw new RuntimeException("Uno o más deportes no existen");
            }
            usuario.setDeportesEntrenados(deportes);
        } else {
            usuario.getDeportesEntrenados().clear();
        }

        Usuario guardado = usuarioRepository.save(usuario);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        modelMapper.map(guardado, responseDTO);

        List<RolResponseDTO> rolesDTO = guardado.getRoles()
                .stream()
                .map(rol -> {
                    RolResponseDTO rolDTO = new RolResponseDTO();
                    modelMapper.map(rol, rolDTO);
                    return rolDTO;
                }).toList();
        responseDTO.setRoles(rolesDTO);

        List<DeporteResponseDTO> deportesDTO = guardado.getDeportesEntrenados()
                .stream()
                .map(deporte -> {
                    DeporteResponseDTO deporteDTO = new DeporteResponseDTO();
                    modelMapper.map(deporte, deporteDTO);
                    deporteDTO.setEntrenadores(new ArrayList<>());
                    return deporteDTO;
                }).toList();
        responseDTO.setDeportesEntrenados(deportesDTO);

        return responseDTO;
    }
    @Transactional
    public UsuarioResponseDTO actualizarUsuario(UUID id, UsuarioUpdateDTO usuarioUpdateDTO) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setNombre(usuarioUpdateDTO.getNombre());
        usuario.setApellido(usuarioUpdateDTO.getApellido());
        usuario.setNumCelular(usuarioUpdateDTO.getNumCelular());
        usuario.setEmail(usuarioUpdateDTO.getEmail());

        if (usuarioUpdateDTO.getPassword() != null && !usuarioUpdateDTO.getPassword().isBlank()) {
            usuario.setPassword(usuarioUpdateDTO.getPassword());
        }

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(usuarioUpdateDTO.getRolesIds()));
        if (roles.size() != usuarioUpdateDTO.getRolesIds().size()) {
            throw new ResourceNotFoundException("Uno o más roles no existen");
        }
        usuario.setRoles(roles);

        boolean esEntrenador = tieneRolEntrenador(roles);
        if (esEntrenador) {
            Set<Deporte> deportes = new HashSet<>(
                    deporteRepository.findAllById(usuarioUpdateDTO.getDeportesEntrenadosIds())
            );
            if (deportes.size() != usuarioUpdateDTO.getDeportesEntrenadosIds().size()) {
                throw new ResourceNotFoundException("Uno o más deportes no existen");
            }
            usuario.setDeportesEntrenados(deportes);
        } else {
            usuario.getDeportesEntrenados().clear();
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        modelMapper.map(actualizado, responseDTO);
        return responseDTO;
    }
    private boolean tieneRolEntrenador(Set<Rol> roles) {
        return roles.stream()
                .anyMatch(rol -> rol.getNombre().equalsIgnoreCase("ENTRENADOR"));
    }
}
