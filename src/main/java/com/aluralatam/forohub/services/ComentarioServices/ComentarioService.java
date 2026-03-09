package com.aluralatam.forohub.services.ComentarioServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aluralatam.forohub.entities.Comentario;
import com.aluralatam.forohub.repository.ComentarioRepository;
import com.aluralatam.forohub.services.TopicServices.TopicService;
import com.aluralatam.forohub.services.UsuarioServices.UsuarioService;
import com.aluralatam.forohub.dtos.ComentarioDtoTransaccion;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TopicService topicService;
    private final UsuarioService usuarioService;

    public List<Comentario> listar(Long topicId) {
        
        if (topicService.checkTopicExist(topicId)) {
            return comentarioRepository.findByTopicId(topicId);
        }
        throw new RuntimeException("Topic no encontrado");
    }

    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    public Comentario guardar(ComentarioDtoTransaccion comentarioDto) {
        Comentario comentario = Comentario.builder()
        .mensaje(comentarioDto.mensaje())
        .author(usuarioService.buscarPorId(comentarioDto.autorId()))
        .topic(topicService.buscarPorId(comentarioDto.topicId()))
        .build();
        
        return comentarioRepository.save(comentario);
    }

    public void eliminarPorId(Long id) {
        comentarioRepository.deleteById(id);
    }
}
