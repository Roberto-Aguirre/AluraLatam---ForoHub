package com.aluralatam.forohub.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluralatam.forohub.dtos.TopicDTO;
import com.aluralatam.forohub.entities.Topic;
import com.aluralatam.forohub.services.TopicServices.TopicMapperService;
import com.aluralatam.forohub.services.TopicServices.TopicService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final TopicMapperService topicMapperService;

    @GetMapping
    public List<TopicDTO> listar() {
        return topicMapperService.toDTOList(topicService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(topicMapperService.toDTO(topicService.buscarPorId(id)));
    }

    @PostMapping
    public Topic guardar(@RequestBody TopicDTO topic) {
        return topicService.guardar(topic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        topicService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
