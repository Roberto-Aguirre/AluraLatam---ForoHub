package com.aluralatam.forohub.services.TopicServices;

import org.springframework.stereotype.Service;

import com.aluralatam.forohub.dtos.TopicDTO;
import com.aluralatam.forohub.repository.TopicRepository;

@Service
public class ValidarMensajeRepetido implements TopicValidation {

    private final TopicRepository topicRepository;

    public ValidarMensajeRepetido(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public void validateTopic(TopicDTO topic) {
        // Lógica para validar que el mensaje del tema no se repita
        // Esto podría implicar consultar la base de datos para verificar si ya existe un tema con el mismo mensaje
        // Si se encuentra un tema con el mismo mensaje, se podría lanzar una excepción o manejarlo según la lógica de negocio
        topicRepository.findByMensaje(topic.mensaje()).ifPresent(existingTopic -> {
            throw new IllegalArgumentException("El mensaje del tema ya existe. Por favor, elige otro mensaje.");
        });
    }


}