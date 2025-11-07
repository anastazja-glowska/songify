package com.songify.infrastructure.security.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageController {

    @GetMapping("/message")
    public ResponseEntity<MessageDto> message(Authentication authentication){
        MessageDto messageDto= new MessageDto("Hi" +  authentication.getName());
        return ResponseEntity.ok(messageDto);
    }
}
