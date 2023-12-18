package ru.practicum.service.users;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.entity.User;
import ru.practicum.exception.NameAlreadyException;
import ru.practicum.exception.UserNotFountException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.qobjects.QUser;
import ru.practicum.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    public List<UserDto> getUsers(Integer[] ids, int from, int size) {
        BooleanBuilder finalQuery = new BooleanBuilder();

        if (Objects.nonNull(ids)) {
            finalQuery.and(QUser.user.id.in(ids));
        }
        return userRepository.findAll(finalQuery, PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id")).getContent().stream().map(UserMapper::fromUser).collect(Collectors.toList());
    }

    public UserDto postUser(NewUserRequest userRequest) {
        User user = UserMapper.toUser(userRequest);
        validate(user);
        return UserMapper.fromUser(userRepository.save(user));
    }

    public void deleteUserById(int id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFountException("Пользователь с id " + id + " не найден"));
        userRepository.deleteById(id);
    }

    private void validate(User user) {
        User userFind = userRepository.findByName(user.getName());
        if (Objects.nonNull(userFind)) {
            throw new NameAlreadyException("User name already in use");
        }
    }
}