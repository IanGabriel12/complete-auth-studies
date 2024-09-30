package com.iangabrieldev.spring_boot_auth.email_confirmation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iangabrieldev.spring_boot_auth.email_confirmation.dto.ConfirmEmailRequestBody;
import com.iangabrieldev.spring_boot_auth.user.UserModel;
import com.iangabrieldev.spring_boot_auth.user.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/user")
public class EmailConfirmationController {
    @Autowired
    private EmailConfirmationService emailConfirmationService;
    @Autowired
    private UserService userService;
    
    @PostMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestBody @Valid ConfirmEmailRequestBody confirmEmailRequestBody) {
        emailConfirmationService.confirmEmail(confirmEmailRequestBody.getToken());
        return ResponseEntity.ok().body("Email confirmed");
    }

    @PostMapping("/send-confirm-email")
    public ResponseEntity<String> askUserToConfirmEmail(@AuthenticationPrincipal String authenticatedUsername) {
        System.out.println(authenticatedUsername);
        UserModel user = userService.findUserByUsername(authenticatedUsername);
        emailConfirmationService.askUserToConfirmEmail(user);
        return ResponseEntity.ok().body("Email confirmation sent");
    }
}
