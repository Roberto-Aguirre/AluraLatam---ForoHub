package com.aluralatam.forohub.services.TopicServices;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.aluralatam.forohub.dtos.TopicDTO;
import com.aluralatam.forohub.entities.EnumRol;
import com.aluralatam.forohub.entities.Topic;
import com.aluralatam.forohub.entities.Usuario;
import com.aluralatam.forohub.exceptions.CustomBadRequestException;
import com.aluralatam.forohub.exceptions.CustomForbiddenException;
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

        Usuario usuarioAutenticado = getUsuarioAutenticado();
        boolean esAdmin = usuarioAutenticado.getRol() == EnumRol.ADMIN;
        if (esAdmin && (topic.usuario() == null || topic.usuario().id() == null)) {
            throw new CustomBadRequestException("Debes enviar usuario.id para crear el topic");
        }
        Long usuarioIdParaGuardar = esAdmin
            ? topic.usuario().id()
            : usuarioAutenticado.getId();

        Topic entity = Topic.builder()
                .titulo(topic.titulo())
                .mensaje(topic.mensaje())
                .estatus(true)
                .curso(cursoService.buscarEntidadPorId(topic.curso().id()).orElseThrow(() -> new CustomNotFoundException("Curso no encontrado")))
            .usuario(usuarioService.buscarEntidadPorId(usuarioIdParaGuardar).orElseThrow(() -> new CustomNotFoundException("Usuario no encontrado")))
                .build()
        ;
        return topicRepository.save(entity);
    }

        public Topic actualizar(Long id, TopicDTO topic) {
        Topic topicExistente = topicRepository.findById(id)
            .orElseThrow(() -> new CustomNotFoundException("Topic no encontrado"));

            Usuario usuarioAutenticado = getUsuarioAutenticado();
            boolean esAdmin = usuarioAutenticado.getRol() == EnumRol.ADMIN;
            if (!esAdmin && !topicExistente.getUsuario().getId().equals(usuarioAutenticado.getId())) {
                throw new CustomForbiddenException("No puedes modificar topics de otros usuarios");
            }
            if (esAdmin && (topic.usuario() == null || topic.usuario().id() == null)) {
                throw new CustomBadRequestException("Debes enviar usuario.id para actualizar el topic");
            }

            Long usuarioIdParaActualizar = esAdmin
                    ? topic.usuario().id()
                    : topicExistente.getUsuario().getId();

            Topic topicActualizado = Topic.builder()
                .id(topicExistente.getId())
                .titulo(topic.titulo())
                .mensaje(topic.mensaje())
                .fechaCreacion(topicExistente.getFechaCreacion())
                .estatus(topic.estatus() != null ? topic.estatus() : topicExistente.isEstatus())
                .curso(cursoService.buscarEntidadPorId(topic.curso().id())
                    .orElseThrow(() -> new CustomNotFoundException("Curso no encontrado")))
                .usuario(usuarioService.buscarEntidadPorId(usuarioIdParaActualizar)
                    .orElseThrow(() -> new CustomNotFoundException("Usuario no encontrado")))
                .comentarios(topicExistente.getComentarios())
                .build();

            return topicRepository.save(topicActualizado);
        }

        private Usuario getUsuarioAutenticado() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new CustomForbiddenException("Usuario no autenticado");
            }
            Object principal = authentication.getPrincipal();

            if (!(principal instanceof Usuario usuario)) {
                throw new CustomForbiddenException("Usuario no autenticado");
            }

            return usuario;
        }

    public void eliminarPorId(Long id) {
        topicRepository.deleteById(id);
    }

    public boolean checkTopicExist(Long topicId) {
        return topicRepository.existsById(topicId);
    }
}
