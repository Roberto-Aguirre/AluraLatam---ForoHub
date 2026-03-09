package com.aluralatam.forohub.services.ComentarioServices;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aluralatam.forohub.entities.EnumRol;
import com.aluralatam.forohub.entities.Comentario;
import com.aluralatam.forohub.entities.Usuario;
import com.aluralatam.forohub.exceptions.CustomBadRequestException;
import com.aluralatam.forohub.exceptions.CustomForbiddenException;
import com.aluralatam.forohub.exceptions.CustomNotFoundException;
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
        Usuario usuarioAutenticado = getUsuarioAutenticado();
        boolean esAdmin = usuarioAutenticado.getRol() == EnumRol.ADMIN;
        if (esAdmin && comentarioDto.autorId() == null) {
            throw new CustomBadRequestException("Debes enviar autor_id para crear el comentario");
        }
        Long autorIdParaGuardar = esAdmin
            ? comentarioDto.autorId()
            : usuarioAutenticado.getId();

        Comentario comentario = Comentario.builder()
        .mensaje(comentarioDto.mensaje())
        .author(usuarioService.buscarPorId(autorIdParaGuardar))
        .topic(topicService.buscarPorId(comentarioDto.topicId()))
        .build();
        
        return comentarioRepository.save(comentario);
    }

    public Comentario actualizar(Long id, ComentarioDtoTransaccion comentarioDto) {
        Comentario comentarioExistente = comentarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Comentario no encontrado"));

        Usuario usuarioAutenticado = getUsuarioAutenticado();
        boolean esAdmin = usuarioAutenticado.getRol() == EnumRol.ADMIN;
        if (!esAdmin && !comentarioExistente.getAuthor().getId().equals(usuarioAutenticado.getId())) {
            throw new CustomForbiddenException("No puedes modificar comentarios de otros usuarios");
        }
        if (esAdmin && comentarioDto.autorId() == null) {
            throw new CustomBadRequestException("Debes enviar autor_id para actualizar el comentario");
        }

        Long autorIdParaActualizar = esAdmin
            ? comentarioDto.autorId()
            : comentarioExistente.getAuthor().getId();

        Comentario comentarioActualizado = Comentario.builder()
            .id(comentarioExistente.getId())
            .mensaje(comentarioDto.mensaje())
            .author(usuarioService.buscarPorId(autorIdParaActualizar))
            .topic(topicService.buscarPorId(comentarioDto.topicId()))
            .build();

        return comentarioRepository.save(comentarioActualizado);
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
        comentarioRepository.deleteById(id);
    }
}
