package com.aluralatam.forohub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluralatam.forohub.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
