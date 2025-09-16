package com.microservice.user.mapper;

import com.microservice.user.dto.UserDto;
import com.microservice.user.model.User;

public final class UserMapper {

    private UserMapper(){}

    public static UserDto parseUserToDto(User user) {
        return new UserDto(user.getName(),
                user.getPassword(),
                user.getDateCreated());
    }

    public static User parseDtoToUser(UserDto userDto) {
        User user = new User();

        user.setName(userDto.username());
        user.setPassword(userDto.password());

        return user;
    }
}
