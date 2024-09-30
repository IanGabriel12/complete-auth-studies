package com.iangabrieldev.spring_boot_auth.email_confirmation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iangabrieldev.spring_boot_auth.user.UserModel;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationToken, Long> {
    Optional<EmailConfirmationToken> findByUser(UserModel user);
    Optional<EmailConfirmationToken> findByToken(String token);
}
