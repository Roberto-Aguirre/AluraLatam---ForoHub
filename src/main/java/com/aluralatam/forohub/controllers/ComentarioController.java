package com.aluralatam.forohub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aluralatam.forohub.dtos.ComentarioDto;
import com.aluralatam.forohub.entities.Comentario;
import com.aluralatam.forohub.services.ComentarioServices.ComentarioService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public List<ComentarioDto> listar(@RequestParam(required = false) Long topic) {
        List<Comentario> comentarios = comentarioService.listar(topic);

        return comentarios.stream().map(ComentarioDto::new).toList();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Comentario> buscarPorId(@PathVariable Long id) {
    //     return comentarioService.buscarPorId(id)
    //             .map(ResponseEntity::ok)
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @PostMapping
    public Comentario guardar(@RequestBody Comentario comentario) {
        return comentarioService.guardar(comentario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        comentarioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
