package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.service.users.UserService;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class UsersController {
    private final UserService userService;

    @GetMapping("/admin/users")
    public List<UserDto> getUsers(@RequestParam(required = false) Integer[] ids, @RequestParam(defaultValue = "0", required = false) int from, @RequestParam(defaultValue = "10", required = false) int size) {
        log.info("Get with user ids={}, from={}, size={}", Arrays.toString(ids), from, size);
        return userService.getUsers(ids, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/users")
    public UserDto postUser(@RequestBody NewUserRequest userRequest) {
        log.info("Post with user = {} ", userRequest.toString());
        return userService.postUser(userRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/users/{userId}")
    public void deleteUser(@PathVariable int userId) {
        log.info("Delete user with id = {}", userId);
        userService.deleteUserById(userId);
    }
}
