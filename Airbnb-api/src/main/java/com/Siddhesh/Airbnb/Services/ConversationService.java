package com.Siddhesh.Airbnb.Services;

import com.Siddhesh.Airbnb.DTOs.ConversationDTO;
import com.Siddhesh.Airbnb.DTOs.MessageDTO;
import com.Siddhesh.Airbnb.Model.Conversation;
import com.Siddhesh.Airbnb.Model.Property;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Repository.ConversationRepository;
import com.Siddhesh.Airbnb.Repository.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @PersistenceContext
    EntityManager entityManager;

    public ConversationService(ConversationRepository conversationRepository,
                               MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public Conversation insertConversation(Long propertyId, Long guestId, Long ownerId) {

        Property property = entityManager.find(Property.class, propertyId);
        if (property == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found");
        }

        User guest = entityManager.find(User.class, guestId);
        if (guest == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest not found");
        }

        User owner = entityManager.find(User.class, ownerId);
        if (owner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
        }

        boolean alreadyExists = conversationRepository
                .findByProperty_IdAndGuest_Id(propertyId, guestId)
                .isPresent();
        if (alreadyExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Conversation already exists for this property and guest");
        }

        Conversation conversation = new Conversation();
        conversation.setProperty(property);
        conversation.setGuest(guest);
        conversation.setOwner(owner);

        return conversationRepository.save(conversation);
    }

    public List<ConversationDTO> getAllConversations() {
        return conversationRepository.findAll().stream()
                .map(conversation -> new ConversationDTO(
                        conversation.getId(),
                        conversation.getProperty().getTitle(),
                        conversation.getGuest().getName(),
                        conversation.getOwner().getName(),
                        messageRepository
                                .findByConversation_IdOrderByTimestampAsc(conversation.getId())
                                .stream()
                                .map(m -> new MessageDTO(
                                        m.getSender().getName(),
                                        m.getMessage(),
                                        m.getTimestamp()
                                ))
                                .toList(),
                        conversation.getCreatedAt()
                ))
                .toList();
    }

    public Conversation fetchConversationById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Conversation not found with id: " + id
                        )
                );
    }

    public List<ConversationDTO> getConversationsByGuestId(Long guestId) {
        List<Conversation> conversations = conversationRepository.findByGuest_Id(guestId);

        return conversations.stream().map(conversation -> {

            List<MessageDTO> messages = messageRepository
                    .findByConversation_IdOrderByTimestampAsc(conversation.getId())
                    .stream()
                    .map(m -> new MessageDTO(
                            m.getSender().getName(),
                            m.getMessage(),
                            m.getTimestamp()
                    ))
                    .toList();

            return new ConversationDTO(
                    conversation.getId(),
                    conversation.getProperty().getTitle(),
                    conversation.getGuest().getName(),
                    conversation.getOwner().getName(),
                    messages,
                    conversation.getCreatedAt()
            );

        }).toList();
    }

    public List<ConversationDTO> getConversationsByOwnerId(Long ownerId) {
        List<Conversation> conversations = conversationRepository.findByOwner_Id(ownerId);

        return conversations.stream().map(conversation -> {

            List<MessageDTO> messages = messageRepository
                    .findByConversation_IdOrderByTimestampAsc(conversation.getId())
                    .stream()
                    .map(m -> new MessageDTO(
                            m.getSender().getName(),
                            m.getMessage(),
                            m.getTimestamp()
                    ))
                    .toList();

            return new ConversationDTO(
                    conversation.getId(),
                    conversation.getProperty().getTitle(),
                    conversation.getGuest().getName(),
                    conversation.getOwner().getName(),
                    messages,
                    conversation.getCreatedAt()
            );

        }).toList();
    }
}