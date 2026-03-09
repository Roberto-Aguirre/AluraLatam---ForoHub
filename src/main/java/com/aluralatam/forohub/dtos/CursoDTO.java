package com.aluralatam.forohub.dtos;

import com.aluralatam.forohub.entities.Curso;
import com.fasterxml.jackson.annotation.JsonAlias;

public record CursoDTO(Long id, @JsonAlias("nombre_curso") String nombreCurso) {

    public CursoDTO(Curso curso) {
        this(curso.getId(), curso.getNombreCurso());
    }
}
