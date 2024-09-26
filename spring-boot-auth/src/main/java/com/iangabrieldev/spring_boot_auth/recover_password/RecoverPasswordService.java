package com.iangabrieldev.spring_boot_auth.recover_password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Optional;
import java.time.LocalDateTime;

import com.iangabrieldev.spring_boot_auth.email.EmailService;
import com.iangabrieldev.spring_boot_auth.user.UserModel;
import com.iangabrieldev.spring_boot_auth.user.UserService;

@Service
public class RecoverPasswordService {
    @Autowired UserService userService;
    @Autowired RecoverPasswordRepository recoverPasswordRepository;
    @Autowired EmailService emailService;

    @Value("${app.constants.recover-password-frontend-uri}")
    String recoverPasswordFrontendURI;

    public String createOrUpdateTokenOfUser(String userEmail) {
        UserModel user = userService.findUserByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        Optional<RecoverPasswordToken> databaseToken = recoverPasswordRepository.findByUser(user);
        RecoverPasswordToken newToken;

        if(databaseToken.isEmpty()) {
            newToken = RecoverPasswordToken
                .builder()
                .token(token)
                .user(user)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();
        } else {
            newToken = databaseToken.get();
            newToken.setExpirationDate(LocalDateTime.now().plusDays(1));
            newToken.setToken(token);
        }

        recoverPasswordRepository.save(newToken);
        return token;
    }

    public void sendEmailWithToken(String userEmail, String token) {
        String message = "Para recuperar sua senha, clique no link a seguir: " + recoverPasswordFrontendURI + token;
        emailService.sendSimpleMessage(userEmail, "Teste autenticação: Recuperação de senha", message);
    }
}
