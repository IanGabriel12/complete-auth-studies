package com.iangabrieldev.spring_boot_auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iangabrieldev.spring_boot_auth.expection.ApiException;
import com.iangabrieldev.spring_boot_auth.storage.impl.FileSystemStorageService;
import com.iangabrieldev.spring_boot_auth.user.dto.AccountCreationRequestBody;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileSystemStorageService storageService;

    /**
     * Creates a new account
     * @param accountCreationRequestBody - AccountCreationRequestBody - Account creation data
     * @return UserModel - Created user
     */
    @Transactional
    public UserModel createAccount(AccountCreationRequestBody accountCreationRequestBody) {
        ensureEmailIsUnique(accountCreationRequestBody.getEmail());
        ensureUsernameIsUnique(accountCreationRequestBody.getUsername());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(accountCreationRequestBody.getPassword());
        UserModel userModel = UserModel.builder()
            .email(accountCreationRequestBody.getEmail())
            .name(accountCreationRequestBody.getName())
            .password(encryptedPassword)
            .username(accountCreationRequestBody.getUsername()).build();
        userRepository.save(userModel);
        String profileImageUrl = storageService.storeProfileImage(accountCreationRequestBody.getProfilePicture());
        userModel.setProfileImageUrl(profileImageUrl);
        userRepository.save(userModel);
        return userModel;
    }

    /**
     * Throws an exception if email is already in use
     * @param email
     */
    private void ensureEmailIsUnique(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ApiException("Email already exists", HttpStatus.CONFLICT);
        });
    }

    /**
     * Throws an exception if username is already in use
     * @param username
     */
    private void ensureUsernameIsUnique(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new ApiException("Username already exists", HttpStatus.CONFLICT);
        });
    }
}
