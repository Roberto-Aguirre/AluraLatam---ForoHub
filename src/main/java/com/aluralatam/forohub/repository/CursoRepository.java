package com.aluralatam.forohub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluralatam.forohub.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
