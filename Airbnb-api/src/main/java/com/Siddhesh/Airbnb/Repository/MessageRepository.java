package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversation_IdOrderByTimestampAsc(Long conversationId);
}