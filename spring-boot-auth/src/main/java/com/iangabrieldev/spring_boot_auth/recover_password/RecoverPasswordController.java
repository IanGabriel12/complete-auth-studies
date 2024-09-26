package com.iangabrieldev.spring_boot_auth.recover_password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iangabrieldev.spring_boot_auth.recover_password.dto.RecoverPasswordRequestBody;

@RestController
@RequestMapping("/api/v1/user")
public class RecoverPasswordController {
    @Autowired RecoverPasswordService recoverPasswordService;
    @PostMapping("/recover-password")
    public ResponseEntity<String> recoverPassword(@RequestBody RecoverPasswordRequestBody recoverPasswordRequestBody) {
        String userEmail = recoverPasswordRequestBody.getEmail();
        String token = recoverPasswordService.createOrUpdateTokenOfUser(userEmail);
        recoverPasswordService.sendEmailWithToken(userEmail, token);
        return ResponseEntity.ok().body("Email to recover password was sent");
    }

    @PutMapping("/update-password")
    public String updatePassword(@PathVariable String id, @RequestBody String entity) {        
        return entity;
    }
}
