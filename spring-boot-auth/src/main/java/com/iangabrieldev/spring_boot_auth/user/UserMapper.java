package com.iangabrieldev.spring_boot_auth.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.iangabrieldev.spring_boot_auth.user.dto.UserView;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {
    public UserView toUserView(UserModel user);
}
