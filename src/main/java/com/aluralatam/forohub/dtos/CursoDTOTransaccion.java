package com.aluralatam.forohub.dtos;

import com.aluralatam.forohub.entities.Curso;
import com.fasterxml.jackson.annotation.JsonAlias;

public record CursoDTOTransaccion(Long id, @JsonAlias("nombre_curso") String nombreCurso) {

    public CursoDTOTransaccion(Curso curso) {
        this(curso.getId(), curso.getNombreCurso());
    }
}
