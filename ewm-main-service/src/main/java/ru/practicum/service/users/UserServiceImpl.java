package ru.practicum.service.users;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserDtoWithSubscribe;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.entity.Subscription;
import ru.practicum.entity.User;
import ru.practicum.exception.NameAlreadyException;
import ru.practicum.exception.SubscribeException;
import ru.practicum.exception.UserNotFountException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.qobjects.QUser;
import ru.practicum.repository.SubscriptionRepository;
import ru.practicum.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    public final SubscriptionRepository subscriptionRepository;

    @Override
    public List<UserDto> getUsers(Integer[] ids, int from, int size) {
        BooleanBuilder finalQuery = new BooleanBuilder();

        if (Objects.nonNull(ids)) {
            finalQuery.and(QUser.user.id.in(ids));
        }
        return userRepository.findAll(finalQuery, PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id")).getContent().stream().map(UserMapper::fromUser).collect(Collectors.toList());
    }

    @Override
    public UserDto postUser(NewUserRequest userRequest) {
        User user = UserMapper.toUser(userRequest);
        validate(user);
        return UserMapper.fromUser(userRepository.save(user));
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFountException("Пользователь с id " + id + " не найден"));
        userRepository.deleteById(id);
    }

    @Override
    public UserDtoWithSubscribe subscribeToEventMaker(int followerId, int eventPublisherId) {
        if (followerId == eventPublisherId) {
            throw new SubscribeException("Follower cant subscribe your self");
        }
        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFountException("Пользователь с id " + followerId + " не найден"));
        User eventPublisher = userRepository.findById(eventPublisherId).orElseThrow(() -> new UserNotFountException("Пользователь с id " + eventPublisherId + " не найден"));
        List<Subscription> subscription = subscriptionRepository.findAllByFollowerId(followerId);
        List<User> eventPublishers = subscription.stream().map(Subscription::getEventMaker).collect(Collectors.toList());
        if (eventPublishers.contains(eventPublisher)) {
            throw new SubscribeException("User already follow to this event publisher");
        }
        eventPublishers.add(eventPublisher);

        Subscription subscriptionSave = Subscription.builder().follower(follower).eventMaker(eventPublisher).build();
        subscriptionRepository.save(subscriptionSave);
        follower.setEventMakers(eventPublishers);
        return UserMapper.toUserDtoSubscribe(follower);
    }

    @Override
    public void deleteEventMakerFromSubscribe(int followerId, int eventMakerId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFountException("Пользователь с id " + followerId + " не найден"));
        User eventPublisher = userRepository.findById(eventMakerId).orElseThrow(() -> new UserNotFountException("Пользователь с id " + eventMakerId + " не найден"));
        Subscription subscription = subscriptionRepository.findByFollowerIdAndEventMakerId(follower.getId(), eventPublisher.getId());
        subscriptionRepository.delete(subscription);
    }

    @Override
    public List<UserShortDto> getUserSubscribesByFollowerId(int followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFountException("Пользователь с id " + followerId + " не найден"));
        List<Subscription> subscription = subscriptionRepository.findAllByFollowerId(follower.getId());
        return subscription.stream().map(e -> UserMapper.toUserShort(e.getEventMaker())).collect(Collectors.toList());
    }

    private void validate(User user) {
        User userFind = userRepository.findByName(user.getName());
        if (Objects.nonNull(userFind)) {
            throw new NameAlreadyException("User name already in use");
        }
    }
}