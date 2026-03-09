package com.aluralatam.forohub.services.UsuarioServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aluralatam.forohub.dtos.UsuarioDTO;
import com.aluralatam.forohub.entities.EnumRol;
import com.aluralatam.forohub.entities.Usuario;
import com.aluralatam.forohub.exceptions.CustomNotFoundException;
import com.aluralatam.forohub.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Optional<Usuario> buscarEntidadPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        Usuario nuevoUsuario = Usuario.builder()
                .id(usuario.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .password(passwordEncoder.encode(usuario.getPassword()))
                .rol(usuario.getRol() != null ? usuario.getRol() : EnumRol.USER)
                .fechaRegistro(usuario.getFechaRegistro())
                .topics(usuario.getTopics())
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Usuario no encontrado"));

        Usuario usuarioActualizado = Usuario.builder()
            .id(usuarioExistente.getId())
            .nombreUsuario(usuario.getNombreUsuario())
            .email(usuario.getEmail())
                .password(passwordEncoder.encode(usuario.getPassword()))
            .rol(usuario.getRol())
            .fechaRegistro(usuarioExistente.getFechaRegistro())
            .topics(usuarioExistente.getTopics())
            .build();

        return usuarioRepository.save(usuarioActualizado);
    }

    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }
}
