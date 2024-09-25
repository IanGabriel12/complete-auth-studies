package com.iangabrieldev.spring_boot_auth.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String storeProfileImage(MultipartFile profileImage);
}
