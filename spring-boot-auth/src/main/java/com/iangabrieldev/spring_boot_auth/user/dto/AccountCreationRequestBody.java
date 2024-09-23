package com.iangabrieldev.spring_boot_auth.user.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountCreationRequestBody {
    private String username;
    private String password;
    private String email;
    private String name;
    private MultipartFile profilePicture;
}
