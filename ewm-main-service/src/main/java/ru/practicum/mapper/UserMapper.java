package ru.practicum.mapper;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserDtoWithSubscribe;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.entity.User;

import java.util.stream.Collectors;

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

    public static UserDtoWithSubscribe toUserDtoSubscribe(User user) {
        return UserDtoWithSubscribe.builder()
                .eventMaker(user.getEventMakers().stream().map(UserMapper::toUserShort).collect(Collectors.toList()))
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();
    }
}
