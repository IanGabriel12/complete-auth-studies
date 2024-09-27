package com.iangabrieldev.spring_boot_auth.storage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UploadView {
    private String resourceUrl;
}
