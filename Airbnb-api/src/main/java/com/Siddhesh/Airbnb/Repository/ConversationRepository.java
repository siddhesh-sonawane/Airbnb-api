package com.Siddhesh.Airbnb.Repository;

import com.Siddhesh.Airbnb.Model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByGuest_Id(Long guestId);
    List<Conversation> findByOwner_Id(Long ownerId);
    Optional<Conversation> findByProperty_IdAndGuest_Id(Long propertyId, Long guestId);
}