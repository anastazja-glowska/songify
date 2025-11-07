package com.songify.domain.userCrud;


import lombok.AllArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UserConformer {

    private final MailSender mailSender;
    private final UserRepository userRepository;

    public void sendConfirmationEmail(User user) {
        String to = user.getEmail();
        String subject = "Confirm your Email";
        String text = "To confirm your email please click here " +
                "https://localhost:8443/users/confirm?token=" + user.getConfirmationToken();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);


    }

    @Transactional
    public boolean confirmUser(String confirmationToken){
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return user.confirm();
    }
}
