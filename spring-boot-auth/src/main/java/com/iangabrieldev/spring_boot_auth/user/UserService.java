package com.iangabrieldev.spring_boot_auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iangabrieldev.spring_boot_auth.expection.ApiException;
import com.iangabrieldev.spring_boot_auth.jwt.JwtService;
import com.iangabrieldev.spring_boot_auth.storage.impl.FileSystemStorageService;
import com.iangabrieldev.spring_boot_auth.user.dto.AccountCreationRequestBody;
import com.iangabrieldev.spring_boot_auth.user.dto.LoginRequestBody;
import com.iangabrieldev.spring_boot_auth.user.dto.LoginView;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileSystemStorageService storageService;
    @Autowired
    private JwtService jwtService;

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

    public LoginView login(LoginRequestBody loginRequestBody) {
        UserModel userModel = userRepository.findByUsername(loginRequestBody.getUsername())
            .orElseThrow(() -> new ApiException("Invalid credentials", HttpStatus.UNAUTHORIZED));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(loginRequestBody.getPassword(), userModel.getPassword())) {
            throw new ApiException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = new UserDetailsImpl(userModel);
        String token = jwtService.generateToken(userDetails);
        Long expiresAt = jwtService.getExpiresAtFromToken(token);
        return LoginView.builder().expiresAt(expiresAt).accessToken(token).build();
    }

    public UserModel findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ApiException("Username not found", HttpStatus.NOT_FOUND));
    }

    public UserModel findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ApiException("Email not found", HttpStatus.NOT_FOUND));
    }

    public void updatePasswordOfUser(UserModel user, String newPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
