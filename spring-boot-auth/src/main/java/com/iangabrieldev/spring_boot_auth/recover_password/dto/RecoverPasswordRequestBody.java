package com.iangabrieldev.spring_boot_auth.recover_password.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoverPasswordRequestBody {
    private String email;
}
