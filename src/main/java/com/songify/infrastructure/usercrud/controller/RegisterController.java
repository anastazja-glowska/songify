package com.songify.infrastructure.usercrud.controller;


import com.songify.domain.userCrud.UserConformer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log4j2
class RegisterController {


    private final UserDetailsManager userDetailsManager;
    private final UserConformer userConformer;


    @PostMapping("/register")
    ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserRequestDto requestDto){
        String password = requestDto.password();
        String username = requestDto.username();
        userDetailsManager.createUser(User.builder()
                .username(username)
                .password(password).build());

        return ResponseEntity.ok(new RegisterUserResponseDto("User registered "));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token){
        boolean isConfirmed = userConformer.confirmUser(token);
        log.info("is confirmed " + isConfirmed);
        return isConfirmed ? "Registration confirmed" : "User not confirmed";
    }
}
