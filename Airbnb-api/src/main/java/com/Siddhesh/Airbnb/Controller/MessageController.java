package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.MessageDTO;
import com.Siddhesh.Airbnb.Model.Message;
import com.Siddhesh.Airbnb.Services.MessageService;
import com.Siddhesh.Airbnb.Utility.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Message>>> fetchAllMessages() {

        List<Message> messages = messageService.fetchAllMessages();

        return ResponseEntity.ok(
                ApiResponse.success("Messages fetched", messages)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Message>> createMessage(
            @RequestParam Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String message
    ) {
        try {
            Message result = messageService.insertMessage(conversationId, senderId, message);
            return ResponseEntity.status(201)
                    .body(ApiResponse.success("Message created", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), "MESSAGE_CREATE_FAILED"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Message>> fetchByMsgId(@PathVariable long id) {

        Message message = messageService.fetchMessageById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Message fetched", message)
        );
    }

    @GetMapping("/chat/{conversationId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getConversationChat(
            @PathVariable Long conversationId) {

        List<MessageDTO> messages = messageService.getConversationChat(conversationId);

        if (messages == null || messages.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("No chat found", "CHAT_NOT_FOUND"));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Chat fetched", messages)
        );
    }
}