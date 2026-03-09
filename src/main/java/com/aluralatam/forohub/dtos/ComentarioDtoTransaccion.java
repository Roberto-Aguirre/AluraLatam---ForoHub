package com.aluralatam.forohub.dtos;
import com.fasterxml.jackson.annotation.JsonAlias;

public record ComentarioDtoTransaccion(String mensaje,@JsonAlias("topic_id") Long topicId, @JsonAlias("autor_id") Long autorId) {

}
