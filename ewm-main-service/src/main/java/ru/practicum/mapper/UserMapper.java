package ru.practicum.mapper;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.entity.User;

public class UserMapper {
    public static UserDto fromUser(User user) {
        return UserDto.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).build();
    }

    public static User toUser(NewUserRequest userRequest) {
        return User.builder().email(userRequest.getEmail()).name(userRequest.getName()).build();
    }

    public static UserShortDto toUserShort(User user) {
        return UserShortDto.builder().id(user.getId()).name(user.getName()).build();
    }

}
