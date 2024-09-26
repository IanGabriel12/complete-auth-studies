package com.iangabrieldev.spring_boot_auth.recover_password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iangabrieldev.spring_boot_auth.user.UserModel;
import java.util.Optional;

@Repository
public interface RecoverPasswordRepository extends JpaRepository<RecoverPasswordToken, Long> {
    public Optional<RecoverPasswordToken> findByUser(UserModel user);
}
