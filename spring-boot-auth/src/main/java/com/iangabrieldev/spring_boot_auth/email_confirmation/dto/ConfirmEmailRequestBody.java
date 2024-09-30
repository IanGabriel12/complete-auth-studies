package com.iangabrieldev.spring_boot_auth.email_confirmation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmEmailRequestBody {
    @NotNull private String token;
}
