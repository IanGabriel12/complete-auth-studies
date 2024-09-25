package com.iangabrieldev.spring_boot_auth.storage.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iangabrieldev.spring_boot_auth.storage.StorageException;
import com.iangabrieldev.spring_boot_auth.storage.StorageProperties;
import com.iangabrieldev.spring_boot_auth.storage.StorageService;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String storeProfileImage(MultipartFile profileImage) {
        if(profileImage.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        if(!isImage(profileImage)) {
            throw new StorageException("Failed to store file, not an image");
        }

        Path destinationFile = this.rootLocation.resolve(
            Paths.get(profileImage.getOriginalFilename())
        ).normalize().toAbsolutePath();

        if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside current directory");
        }

        try {
            InputStream inputStream = profileImage.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + profileImage.getOriginalFilename(), e);
        }
        try {
            return InetAddress.getLocalHost().getHostName() + destinationFile.toString();
        } catch (UnknownHostException e) {
            throw new StorageException("Failed to get hostname", e);
        }
    }
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Failed to initialize storage", e);
        }
    }

    private Boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }
}
