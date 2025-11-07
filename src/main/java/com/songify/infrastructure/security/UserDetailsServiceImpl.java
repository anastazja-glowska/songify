package com.songify.infrastructure.security;

import com.songify.domain.userCrud.User;
import com.songify.domain.userCrud.UserConformer;
import com.songify.domain.userCrud.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Log4j2
@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsManager {


    private static final String DEFAULT_USER_ROLE = "ROLE_USER";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConformer userConformer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void createUser(UserDetails user) {
            if(userExists(user.getUsername())) {
                log.warn("Username {} already exists - user not saved", user.getUsername());
                throw  new RuntimeException("Username already exists");
            }

        String confirmationToken = UUID.randomUUID().toString();
        User createdUser = new User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                confirmationToken,
                List.of(DEFAULT_USER_ROLE)
        );

        User savedUser = userRepository.save(createdUser);
        userConformer.sendConfirmationEmail(createdUser);
        log.info("User {} created successfully", savedUser.getId());
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }
}
