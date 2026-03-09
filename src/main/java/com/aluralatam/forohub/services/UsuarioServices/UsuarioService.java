package com.aluralatam.forohub.services.UsuarioServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aluralatam.forohub.dtos.UsuarioDTO;
import com.aluralatam.forohub.entities.Usuario;
import com.aluralatam.forohub.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioDTO::new);
    }

    public Optional<Usuario> buscarEntidadPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }
}
