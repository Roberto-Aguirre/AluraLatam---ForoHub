package com.aluralatam.forohub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluralatam.forohub.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    Optional<Usuario> findOptionalByEmail(String email);
}
