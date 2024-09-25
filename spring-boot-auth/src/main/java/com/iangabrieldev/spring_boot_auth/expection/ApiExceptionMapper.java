package com.iangabrieldev.spring_boot_auth.expection;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ApiExceptionMapper {
    ApiExceptionView toApiExceptionView(ApiException apiException);
}
