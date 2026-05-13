package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.MessageDTO;
import com.Siddhesh.Airbnb.Model.Conversation;
import com.Siddhesh.Airbnb.Model.Message;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @PersistenceContext
    EntityManager entityManager;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message insertMessage(Long conversationId, Long senderId, String messageText) {

        Conversation conversation = entityManager.find(Conversation.class, conversationId);
        if (conversation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conversation not found");
        }

        User sender = entityManager.find(User.class, senderId);
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found");
        }

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setMessage(messageText);

        return messageRepository.save(message);
    }

    public List<Message> fetchAllMessages() {
        return messageRepository.findAll();
    }

    public Message fetchMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Message not found with id: " + id
                        )
                );
    }

    public List<MessageDTO> getConversationChat(Long conversationId) {

        List<Message> messages = messageRepository
                .findByConversation_IdOrderByTimestampAsc(conversationId);

        return messages.stream()
                .map(m -> new MessageDTO(
                        m.getSender().getName(),
                        m.getMessage(),
                        m.getTimestamp()
                ))
                .toList();
    }
}