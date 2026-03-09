package com.aluralatam.forohub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluralatam.forohub.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByTitulo(String titulo);

    Optional<Topic> findByMensaje(String mensaje);
}
