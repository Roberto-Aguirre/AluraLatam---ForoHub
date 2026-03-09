package com.aluralatam.forohub.services.TopicServices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aluralatam.forohub.dtos.TopicDTO;
import com.aluralatam.forohub.entities.Topic;
import com.aluralatam.forohub.exceptions.CustomNotFoundException;
import com.aluralatam.forohub.repository.TopicRepository;
import com.aluralatam.forohub.services.CursoServices.CursoService;
import com.aluralatam.forohub.services.UsuarioServices.UsuarioService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final CursoService cursoService;
    private final UsuarioService usuarioService;
    private final List<TopicValidation> topicValidation;

    public List<Topic> listar() {
        return topicRepository.findAll();
    }

    public Topic buscarPorId(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic no encontrado"));
    }

    public Topic guardar(TopicDTO topic) {
        topicValidation.forEach(validation -> validation.validateTopic(topic));
        Topic entity = Topic.builder()
                .titulo(topic.titulo())
                .mensaje(topic.mensaje())
                .estatus(true)
                .curso(cursoService.buscarEntidadPorId(topic.curso().id()).orElseThrow(() -> new CustomNotFoundException("Curso no encontrado")))
                .usuario(usuarioService.buscarEntidadPorId(topic.usuario().id()).orElseThrow(() -> new CustomNotFoundException("Usuario no encontrado")))
                .build()
        ;
        return topicRepository.save(entity);
    }

    public void eliminarPorId(Long id) {
        topicRepository.deleteById(id);
    }

    public boolean checkTopicExist(Long topicId) {
        return topicRepository.existsById(topicId);
    }
}
