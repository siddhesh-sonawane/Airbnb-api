package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.ConversationDTO;
import com.Siddhesh.Airbnb.Model.Conversation;
import com.Siddhesh.Airbnb.Services.ConversationService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ConversationDTO>>> getAllConversations() {

        List<ConversationDTO> conversations = conversationService.getAllConversations();

        return ResponseEntity.ok(
                ApiResponse.success("Conversations fetched", conversations)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Conversation>> createConversation(
            @RequestParam Long propertyId,
            @RequestParam Long guestId,
            @RequestParam Long ownerId
    ) {
        try {
            Conversation result = conversationService.insertConversation(
                    propertyId, guestId, ownerId
            );
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Conversation created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "CONVERSATION_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Conversation>> fetchByConversationId(@PathVariable long id) {

        Conversation conversation = conversationService.fetchConversationById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Conversation fetched", conversation)
        );
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<ApiResponse<List<ConversationDTO>>> getConversationsByGuestId(
            @PathVariable Long guestId) {

        List<ConversationDTO> conversations = conversationService
                .getConversationsByGuestId(guestId);

        return ResponseEntity.ok(
                ApiResponse.success("Guest conversations fetched", conversations)
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<ConversationDTO>>> getConversationsByOwnerId(
            @PathVariable Long ownerId) {

        List<ConversationDTO> conversations = conversationService
                .getConversationsByOwnerId(ownerId);

        return ResponseEntity.ok(
                ApiResponse.success("Owner conversations fetched", conversations)
        );
    }
}