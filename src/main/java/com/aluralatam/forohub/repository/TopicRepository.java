package com.aluralatam.forohub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluralatam.forohub.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
