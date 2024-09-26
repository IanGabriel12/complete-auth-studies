package com.iangabrieldev.spring_boot_auth.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageProperties {
    private String location = "src/main/resources/static/images";
}
