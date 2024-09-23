package com.iangabrieldev.spring_boot_auth.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iangabrieldev.spring_boot_auth.user.dto.AccountCreationRequestBody;
import com.iangabrieldev.spring_boot_auth.user.dto.LoginRequestBody;
import com.iangabrieldev.spring_boot_auth.user.dto.MeResponseBody;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestBody loginRequestBody) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountCreationRequestBody accountCreationRequestBody) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseBody> me() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PostMapping("/recover")
    public ResponseEntity<String> recover(@RequestBody String email) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
