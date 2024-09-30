package com.iangabrieldev.spring_boot_auth.email_confirmation;

import java.time.LocalDateTime;

import com.iangabrieldev.spring_boot_auth.user.UserModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String token;
    @OneToOne
    private UserModel user;

    private LocalDateTime expirationDate;
}
