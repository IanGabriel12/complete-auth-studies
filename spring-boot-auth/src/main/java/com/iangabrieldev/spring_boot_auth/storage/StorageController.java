package com.iangabrieldev.spring_boot_auth.storage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iangabrieldev.spring_boot_auth.storage.dto.UploadView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/v1/upload")
public class StorageController {
    @Autowired private StorageService storageService;
    @PostMapping("/image")
    public ResponseEntity<UploadView> uploadImage(@RequestPart MultipartFile image) {
        String imageUrl = storageService.storeImage(image);
        return ResponseEntity.ok().body(UploadView.builder().resourceUrl(imageUrl).build());
    }
}
