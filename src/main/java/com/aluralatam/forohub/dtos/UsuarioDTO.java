package com.aluralatam.forohub.dtos;

import com.aluralatam.forohub.entities.Usuario;

public record UsuarioDTO(Long id, String usuario) {
    
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNombreUsuario());
    }
}
