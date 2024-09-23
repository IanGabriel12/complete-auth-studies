package com.iangabrieldev.spring_boot_auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iangabrieldev.spring_boot_auth.storage.impl.FileSystemStorageService;
import com.iangabrieldev.spring_boot_auth.user.dto.AccountCreationRequestBody;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileSystemStorageService storageService;

    public UserModel createAccount(AccountCreationRequestBody accountCreationRequestBody) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(accountCreationRequestBody.getPassword());
        String profileImageUrl = storageService.storeImage(accountCreationRequestBody.getProfilePicture());
        UserModel userModel = UserModel.builder()
            .email(accountCreationRequestBody.getEmail())
            .name(accountCreationRequestBody.getName())
            .password(encryptedPassword)
            .profileImageUrl(profileImageUrl)
            .username(accountCreationRequestBody.getUsername()).build();
        return userRepository.save(userModel);
    }
}
