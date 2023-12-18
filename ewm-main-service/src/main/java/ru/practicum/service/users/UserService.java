package ru.practicum.service.users;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserDtoWithSubscribe;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(Integer[] ids, int from, int size);

    UserDto postUser(NewUserRequest userRequest);

    void deleteUserById(int id);

    UserDtoWithSubscribe subscribeToEventMaker(int folowerId, int eventPulisherId);

    void deleteEventMakerFromSubscribe(int followerId, int eventMakerId);

    List<UserDtoWithSubscribe> getUserSubscribesByFollowerId(int followerId);
}
