package com.iangabrieldev.spring_boot_auth.recover_password.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdatePasswordRequestBody {
    private String token;
    private String newPassword;
}
