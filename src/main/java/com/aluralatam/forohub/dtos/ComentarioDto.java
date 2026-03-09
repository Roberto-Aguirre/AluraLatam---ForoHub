package com.aluralatam.forohub.dtos;
import com.aluralatam.forohub.entities.Comentario;

public record ComentarioDto(String mensaje, String autor) {
    
    public ComentarioDto(Comentario comentario) {
        this(comentario.getMensaje(), comentario.getAuthor().getNombreUsuario());
    }

}
