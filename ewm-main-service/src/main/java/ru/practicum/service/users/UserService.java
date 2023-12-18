package ru.practicum.service.users;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(Integer[] ids, int from, int size);

    UserDto postUser(NewUserRequest userRequest);

    void deleteUserById(int id);
}
