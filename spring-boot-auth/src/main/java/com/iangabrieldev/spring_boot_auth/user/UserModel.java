package com.iangabrieldev.spring_boot_auth.user;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "users")
public class UserModel {
    @Id
    private Long id;

    @Column(unique = true)
    @Nonnull
    private String username;

    @Column
    @Nonnull
    private String password;

    @Column(unique = true)
    @Nonnull
    private String email;

    @Column
    @Nonnull
    private String name;

    @Column(unique = true)
    private String profileImageUrl;
}
