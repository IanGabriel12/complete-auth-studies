package com.iangabrieldev.spring_boot_auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserView {
    private String username;
    private String email;
    private String profileImageUrl;
    private String name;
}
