package com.iangabrieldev.spring_boot_auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iangabrieldev.spring_boot_auth.user.dto.AccountCreationRequestBody;
import com.iangabrieldev.spring_boot_auth.user.dto.LoginRequestBody;
import com.iangabrieldev.spring_boot_auth.user.dto.LoginView;
import com.iangabrieldev.spring_boot_auth.user.dto.UserView;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired UserService userService;
    @Autowired UserMapper userMapper;
    @PostMapping("/login")
    public ResponseEntity<LoginView> login(@RequestBody LoginRequestBody loginRequestBody) {
        LoginView loginView = userService.login(loginRequestBody);
        return ResponseEntity.ok().body(loginView);
    }

    @PostMapping("/register")
    public ResponseEntity<UserView> register(@ModelAttribute @Valid AccountCreationRequestBody accountCreationRequestBody) {
        UserModel createdAccount = userService.createAccount(accountCreationRequestBody);
        return ResponseEntity.ok().body(userMapper.toUserView(createdAccount));
    }

    @GetMapping("/me")
    public ResponseEntity<UserView> me(@AuthenticationPrincipal String authenticatedUsername) {
        UserModel account = userService.findUserByUsername(authenticatedUsername);
        return ResponseEntity.ok().body(userMapper.toUserView(account));
    }
}
