package com.iangabrieldev.spring_boot_auth.user.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull private String username;
    @NotNull private String password;
    @NotNull private String email;
    @NotNull private String name;
    @NotNull private String profileImageUrl;
}
