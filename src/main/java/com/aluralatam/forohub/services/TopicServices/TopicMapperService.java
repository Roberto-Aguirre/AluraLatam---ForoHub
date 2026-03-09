package com.aluralatam.forohub.services.TopicServices;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aluralatam.forohub.dtos.TopicDTO;
import com.aluralatam.forohub.entities.Topic;

@Component
public class TopicMapperService {
    
    public TopicDTO toDTO(Topic topic) {
        return new TopicDTO(topic);
    }

    public List<TopicDTO> toDTOList(List<Topic> topics) {
        return topics.stream().map(this::toDTO).toList();
    }
}
