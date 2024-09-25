package com.iangabrieldev.spring_boot_auth.expection;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionView {
    private String message;
    private HttpStatus httpStatus;
}
