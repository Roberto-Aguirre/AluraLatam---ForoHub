package com.aluralatam.forohub.dtos;

import com.aluralatam.forohub.entities.Topic;

public record TopicDTO(Long id, String titulo, String mensaje, Boolean estatus, CursoDTO curso, UsuarioDTO usuario, String fechaCreacion, Long respuestas) {

    public TopicDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitulo(),
                topic.getMensaje(),
                topic.isEstatus(),
                topic.getCurso() != null ? new CursoDTO(topic.getCurso()) : null,
                // null,
                // null
                topic.getUsuario() != null ? new UsuarioDTO(topic.getUsuario()) : null,
                topic.getFechaCreacion() != null ? topic.getFechaCreacion().toString() : null,
                topic.getComentarios() != null ? (long) topic.getComentarios().size() : 0
            );
    }
}
