package com.iangabrieldev.spring_boot_auth.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadProfilePictureView {
    private String profileImageUrl;
}
