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
import ru.practicum.exception.UserEmailException;
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
        if (Objects.isNull(user.getEmail()) || user.getEmail().isBlank() || user.getEmail().isEmpty()) {
            throw new UserEmailException("Email balnk or empty");
        }

        if (Objects.isNull(user.getName()) || user.getName().isEmpty() || user.getName().isBlank()) {
            throw new UserEmailException("User name is empty or blank");
        }

        if (user.getName().length() < 2) {
            throw new UserEmailException("User name length less 2 symbols");
        }

        if (user.getName().length() > 250) {
            throw new UserEmailException("User name length large then 250 symbols");
        }

        String[] emailParts = user.getEmail().split("@");
        if (emailParts[0].length() > 64) {
            throw new UserEmailException("User email localpart length large than 64 symbols");
        }
        String[] domain = emailParts[1].split("\\.");
        if (domain[0].length() > 63) {
            throw new UserEmailException("User email domain length large than 63 symbols");
        }

        if (user.getEmail().length() < 6) {
            throw new UserEmailException("User email  length less 6 symbols");
        }

        if (user.getEmail().length() > 254) {
            throw new UserEmailException(("User email length large then 254 symbols"));
        }

        User userFind = userRepository.findByName(user.getName());
        if (Objects.nonNull(userFind)) {
            throw new NameAlreadyException("User name already in use");
        }
    }
}