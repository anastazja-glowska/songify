package com.songify.infrastructure.usercrud.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
class RegisterController {

    private final UserDetailsManager userDetailsManager;


    @PostMapping("/register")
    ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserRequestDto requestDto){
        String password = requestDto.password();
        String username = requestDto.username();
        userDetailsManager.createUser(User.builder()
                .username(username)
                .password(password).build());

        return ResponseEntity.ok(new RegisterUserResponseDto("User registered "));
    }
}
