package ru.practicum.service.event;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.dto.user.UpdateEventUserRequest;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.entity.*;
import ru.practicum.exception.*;
import ru.practicum.mapper.EventMapper;
import ru.practicum.qobjects.QEvent;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.ParticipationRequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.StatsService;
import ru.practicum.service.users.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository participationRequestRepository;
    private final StatsService statsService;
    private final UserService userService;


    @Override
    public List<EventFullDto> getEvents(Integer[] users, String[] states, Integer[] categories, String rangeStart, String rangeEnd, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start;
        LocalDateTime end;

        BooleanExpression byUsersId;
        BooleanExpression byStatesId;
        BooleanExpression byCategories;
        BooleanExpression byDate;
        BooleanBuilder result = new BooleanBuilder();

        if (Objects.nonNull(rangeStart) || Objects.nonNull(rangeEnd)) {
            start = LocalDateTime.parse(rangeStart, dateTimeFormatter);
            end = LocalDateTime.parse(rangeEnd, dateTimeFormatter);
            byDate = QEvent.event.eventDate.between(start, end);
            result.and(byDate);
        }
        if (Objects.nonNull(users)) {
            byUsersId = QEvent.event.initiator.id.in(users);
            result.and(byUsersId);
        }
        if (Objects.nonNull(states)) {
            List<State> stateList = Arrays.stream(states).map(State::valueOf).collect(Collectors.toList());
            byStatesId = QEvent.event.state.in(stateList);
            result.and(byStatesId);
        }
        if (Objects.nonNull(categories)) {
            byCategories = QEvent.event.category.id.in(categories);
            result.and(byCategories);
        }


        log.info("users = {} and categories = {}", users, categories);
        return eventRepository.findAll(result, pageRequest)
                .getContent()
                .stream().map(EventMapper::toEventFullDto)
                .peek(e -> e.setConfirmedRequests(participationRequestRepository.findByEvent(e.getId()).stream().filter(e1 -> e1.getStatus().equals(State.CONFIRMED.toString())).count())).collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateEventAdmin(UpdateEventAdminRequest updateEventAdminRequest, int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Такого эвента нет"));

        if (Objects.nonNull(updateEventAdminRequest.getPaid())) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }

        if (Objects.nonNull(updateEventAdminRequest.getParticipantLimit())) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }

        if (Objects.nonNull(updateEventAdminRequest.getTitle())) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        if (Objects.nonNull(updateEventAdminRequest.getRequestModeration())) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }

        if (Objects.nonNull(updateEventAdminRequest.getDescription())) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }

        if (Objects.nonNull(updateEventAdminRequest.getAnnotation())) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }

        if (Objects.nonNull(updateEventAdminRequest.getEventDate())) {
            LocalDateTime eventDateUpdate = LocalDateTime.parse(updateEventAdminRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (eventDateUpdate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new WrongPostEventException("Time wrong");
            }
            event.setEventDate(eventDateUpdate);
        }

        if (Objects.nonNull(updateEventAdminRequest.getCategory())) {
            int category = updateEventAdminRequest.getCategory();
            event.setCategory(Category.builder().id(category).build());
        }
        if (Objects.nonNull(updateEventAdminRequest.getStateAction())) {
            if (State.PUBLISH_EVENT.equals(State.valueOf(updateEventAdminRequest.getStateAction()))) {
                if (State.PENDING.equals(event.getState())) {
                    event.setPublishedOn(LocalDateTime.now());
                    event.setState(State.PUBLISHED);
                } else {
                    throw new CannotPublishedException("Cannot publish the event because it's not in the right state: PUBLISHED");
                }
            } else if (State.REJECT_EVENT.toString().equals(updateEventAdminRequest.getStateAction())) {
                if (State.PENDING.equals(event.getState())) {
                    event.setPublishedOn(LocalDateTime.now());
                    event.setState(State.CANCELED);
                } else {
                    throw new CannotPublishedException("Cannot publish the event because it's not in the right state: PUBLISHED");
                }
            } else {
                event.setState(State.valueOf(updateEventAdminRequest.getStateAction()));
            }
        }
        validateEvent(event);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getEventsByUserId(int userId, int from, int size) {
        return eventRepository.findByInitiatorId(userId, PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id")).getContent().stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto postEvent(int userId, NewEventDto eventDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFountException("Пользователь не найден"));
        Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(() -> new NotFoundCategoryException("Категория не найдена"));
        Event event = EventMapper.fromNewDto(eventDto);

        validateEvent(event);
        event.setInitiator(user);
        event.setCategory(category);
        event.setState(State.PENDING);
        if (Objects.nonNull(eventDto.getRequestModeration())) {
            event.setRequestModeration(eventDto.getRequestModeration());
        } else {
            event.setRequestModeration(true);
        }
        log.info("Post event with event = {}", event);
        Event saveEvent = eventRepository.save(event);
        saveEvent.setInitiator(user);
        saveEvent.setCategory(category);

        return EventMapper.toEventFullDto(saveEvent);
    }

    private void validateEvent(Event event) {
        if (!event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new WrongPostEventException("Field: eventDate. Error: должно содержать дату, которая еще не наступила. Value:" + event.getEventDate());
        }
    }

    @Override
    public EventFullDto getEventByIdAndUserId(int eventId, int userId) {
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundEventException("Евент не найден")));
        long confirmedRequest = participationRequestRepository.findByEvent(eventId).stream().filter(e -> e.getStatus().equals(State.CONFIRMED.toString())).count();
        eventFullDto.setConfirmedRequests(confirmedRequest);
        return eventFullDto;
    }

    @Override
    public EventFullDto putEventByIdUserAndIdEvent(int userId, int eventId, UpdateEventUserRequest updateEventAdminRequest) {

        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId).orElseThrow(() -> new NotFoundEventException("Event with id= " + eventId + " was not found"));
        boolean isPending = State.PENDING.equals(event.getState());
        boolean isCanceled = State.CANCELED.equals(event.getState());
        if (!isPending && !isCanceled) {
            throw new CannotRequestException("Only pending or canceled events can be changed");
        }

        if (Objects.nonNull(updateEventAdminRequest.getStateAction())) {
            State stateUpdate = State.valueOf(updateEventAdminRequest.getStateAction());
            if (State.PUBLISH_EVENT.equals(stateUpdate)) {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(State.PUBLISHED);
            } else if (State.REJECT_EVENT.equals(stateUpdate)) {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(State.CANCELED);
            } else if (State.SEND_TO_REVIEW.equals(stateUpdate)) {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(State.PENDING);
            } else if (State.CANCEL_REVIEW.equals(stateUpdate)) {
                event.setState(State.CANCELED);
            }
        }

        if (Objects.nonNull(updateEventAdminRequest.getPaid())) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }

        if (Objects.nonNull(updateEventAdminRequest.getRequestModeration())) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }

        if (Objects.nonNull(updateEventAdminRequest.getDescription())) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }

        if (Objects.nonNull(updateEventAdminRequest.getAnnotation())) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }

        if (Objects.nonNull(updateEventAdminRequest.getEventDate())) {
            LocalDateTime eventDateUpdate = LocalDateTime.parse(updateEventAdminRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (eventDateUpdate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new WrongPostEventException("Time wrong");
            }
            event.setEventDate(eventDateUpdate);
        }

        if (Objects.nonNull(updateEventAdminRequest.getCategory())) {
            int category = updateEventAdminRequest.getCategory();
            event.setCategory(Category.builder().id(category).build());
        }

        if (Objects.nonNull(updateEventAdminRequest.getTitle())) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        if (Objects.nonNull(updateEventAdminRequest.getLocation())) {
            Location location = updateEventAdminRequest.getLocation();
            event.setLat(location.getLat());
            event.setLon(location.getLon());
        }
        validateEvent(event);

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getAllEvents(String text, Integer[] categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id");
        Iterable<Event> events;
        BooleanBuilder result = new BooleanBuilder();
        if (Objects.nonNull(text)) {
            BooleanExpression byTextAnnotation = QEvent.event.annotation.containsIgnoreCase(text);
            BooleanExpression byTextDescription = QEvent.event.description.containsIgnoreCase(text);
            result.and(byTextAnnotation).or(byTextDescription);
        }

        if (Objects.nonNull(paid)) {
            BooleanExpression byPaid = QEvent.event.paid.eq(paid);
            result.and(byPaid);
        }

        if (Objects.nonNull(categories)) {
            List<Category> categoryList = categoryRepository.findAllById(Arrays.asList(categories));
            BooleanExpression byCategories = QEvent.event.category.in(categoryList);
            result.and(byCategories);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (Objects.nonNull(rangeEnd) || Objects.nonNull(rangeStart)) {

            LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
            LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
            if (end.isBefore(start)) {
                throw new WrongSearchTimeIntervalException("Event must be published");
            }
            BooleanExpression byRange = QEvent.event.eventDate.between(start, end);

            result.and(byRange);

            events = eventRepository.findAll(result, pageRequest);

        } else {
            BooleanExpression byDate = QEvent.event.eventDate.after(LocalDateTime.now());
            result.and(byDate);
            events = eventRepository.findAll(result, pageRequest);

        }
        List<EventShortDto> eventShortDtos = new ArrayList<>();
        for (Event e : events) {
            e.setViews(getViews(e.getId()));
            eventShortDtos.add(EventMapper.toShortDto(e));
        }
        if (sort.equals("EVENT_DATE")) {
            eventShortDtos = eventShortDtos.stream().sorted(Comparator.comparing(e -> LocalDateTime.parse(e.getEventDate(), formatter))).collect(Collectors.toList());
        }
        if (sort.equals("VIEWS")) {
            eventShortDtos = eventShortDtos.stream().sorted(Comparator.comparing(EventShortDto::getViews)).collect(Collectors.toList());
        }

        return eventShortDtos;
    }

    @Override
    public EventFullDto getEventById(int id) {
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(() -> new NotFoundEventException("Event not found")));
        long confirmedRequest = participationRequestRepository.findByEvent(id).stream().filter(e -> State.valueOf(e.getStatus()).equals(State.CONFIRMED)).count();
        eventFullDto.setConfirmedRequests(confirmedRequest);
        eventFullDto.setViews(getViews(id));
        return eventFullDto;
    }

    @Override
    public List<EventFullDto> getEventSubscribesByFollowerId(int followerId, State state) {
        if (State.PUBLISH_EVENT.equals(state)) {
            state = State.PUBLISHED;
        } else if (State.REJECT_EVENT.equals(state)) {
            state = State.CANCELED;
        } else if (State.SEND_TO_REVIEW.equals(state)) {
            state = State.PENDING;
        } else if (State.CANCEL_REVIEW.equals(state)) {
            state = State.CANCELED;
        }

        User follower = userRepository.findById(followerId).orElseThrow(() -> new UserNotFountException("Пользователь с id " + followerId + " не найден"));
        List<EventFullDto> subscribeEvents = new ArrayList<>();
        List<Integer> eventPublishers = userService.getUserSubscribesByFollowerId(followerId).stream().map(UserShortDto::getId).collect(Collectors.toList());
        if (eventPublishers.isEmpty()) {
            subscribeEvents = eventRepository.findAllByInitiatorIdInAndState(eventPublishers, state).stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
        }

        return subscribeEvents;
    }

    @Override
    public int getViews(int id) {
        String[] eventsPoint = {"/events/" + id};
        return statsService.getStatistic(LocalDateTime.now().minusYears(1), LocalDateTime.now().plusDays(1), eventsPoint, "true").size();
    }


}
