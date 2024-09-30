package com.iangabrieldev.spring_boot_auth.email_confirmation;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.iangabrieldev.spring_boot_auth.email.EmailService;
import com.iangabrieldev.spring_boot_auth.expection.ApiException;
import com.iangabrieldev.spring_boot_auth.user.UserModel;

@Service
public class EmailConfirmationService {
    @Autowired
    private EmailConfirmationRepository emailConfirmationRepository;

    @Autowired
    private EmailService emailService;

    @Value("${frontend.url}")
    private String frontendUrl;

    public void askUserToConfirmEmail(UserModel user) {
        if(!user.getEmailConfirmed()) {
            EmailConfirmationToken emailToken = generateNewConfirmationToken(user);
            sendConfirmationEmail(user, emailToken.getToken());
        }
    }

    private EmailConfirmationToken generateNewConfirmationToken(UserModel user) {
        EmailConfirmationToken emailConfirmationToken = emailConfirmationRepository
            .findByUser(user)
            .orElse(
                EmailConfirmationToken.builder()
                    .user(user)
                    .build()
            );

        emailConfirmationToken.setToken(UUID.randomUUID().toString());
        return emailConfirmationRepository.save(emailConfirmationToken);
    }

    private void sendConfirmationEmail(UserModel user, String token) {
        String confirmationUrl = frontendUrl + "/?token=" + token;
        String message = "Welcome, " + user.getName() + ".To confirm your email, click on the following link: " + confirmationUrl;
        emailService.sendSimpleMessage(user.getEmail(), "Email confirmation", message);
    }

    public void confirmEmail(String token) {
        EmailConfirmationToken emailConfirmationToken = emailConfirmationRepository
            .findByToken(token)
            .orElseThrow(() -> new ApiException("Invalid token", HttpStatus.BAD_REQUEST));
        
        if(emailConfirmationToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ApiException("Token expired", HttpStatus.BAD_REQUEST);
        }

        UserModel user = emailConfirmationToken.getUser();
        user.setEmailConfirmed(true);
        emailConfirmationRepository.delete(emailConfirmationToken);
    }
}
