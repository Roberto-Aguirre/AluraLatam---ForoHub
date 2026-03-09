package com.aluralatam.forohub.services.TopicServices;

import org.springframework.stereotype.Service;

import com.aluralatam.forohub.dtos.TopicDTO;
import com.aluralatam.forohub.repository.TopicRepository;

@Service
public class ValidarTituloRepetido implements TopicValidation {

    private final TopicRepository topicRepository;

    public ValidarTituloRepetido(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void validateTopic(TopicDTO topic) {
        // Lógica para validar que el título del tema no se repita
        // Esto podría implicar consultar la base de datos para verificar si ya existe un tema con el mismo título
        // Si se encuentra un tema con el mismo título, se podría lanzar una excepción o manejarlo según la lógica de negocio
        topicRepository.findByTitulo(topic.titulo()).ifPresent(existingTopic -> {
            throw new IllegalArgumentException("El título del tema ya existe. Por favor, elige otro título.");
        });
    }


}