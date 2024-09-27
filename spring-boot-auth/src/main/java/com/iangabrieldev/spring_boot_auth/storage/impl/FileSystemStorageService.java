package com.iangabrieldev.spring_boot_auth.storage.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iangabrieldev.spring_boot_auth.storage.StorageException;
import com.iangabrieldev.spring_boot_auth.storage.StorageProperties;
import com.iangabrieldev.spring_boot_auth.storage.StorageService;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Value("${storage.images.url}")
    private String imagesUrl;

    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String storeImage(MultipartFile image) {
        if(image.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        if(!isImage(image)) {
            throw new StorageException("Failed to store file, not an image");
        }

        String randomUuid = UUID.randomUUID().toString();
        String[] imageNameParts = image.getOriginalFilename().split("\\.", 2);
        String imageExtension = imageNameParts[1];
        String imageName = randomUuid + "." + imageExtension;
        Path destinationFile = this.rootLocation.resolve(
            Paths.get(imageName)
        ).normalize().toAbsolutePath();

        if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside current directory");
        }

        try {
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + image.getOriginalFilename(), e);
        }
        return imagesUrl + "/" + imageName;
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
