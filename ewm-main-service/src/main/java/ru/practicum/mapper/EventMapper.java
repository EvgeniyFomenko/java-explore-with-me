package ru.practicum.mapper;

import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.user.UpdateEventUserRequest;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.entity.Location;
import ru.practicum.entity.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class EventMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder().eventDate(event.getEventDate().format(formatter))
                .id(event.getId())
                .paid(event.isPaid())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews())
                .description(event.getDescription())
                .initiator(UserMapper.toUserShort(event.getInitiator()))
                .location(Location.builder().lon(event.getLon()).lat(event.getLat()).build())
                .createdOn(event.getCreatedOn().toString())
                .annotation(event.getAnnotation())
                .participantLimit(event.getParticipantLimit())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .requestModeration(event.isRequestModeration())
                .publishedOn(Objects.isNull(event.getPublishedOn()) ? null : event.getPublishedOn().format(formatter))
                .build();
    }

    public static Event fromUpdateUserEvent(UpdateEventUserRequest updateEventAdminRequest) {

        return Event.builder()
                .annotation(updateEventAdminRequest.getAnnotation())
                .category(Category.builder().id(updateEventAdminRequest.getCategory()).build())
                .description(updateEventAdminRequest.getDescription())
                .eventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter))
                .lat(updateEventAdminRequest.getLocation().getLat())
                .lon(updateEventAdminRequest.getLocation().getLon())
                .paid(updateEventAdminRequest.getPaid())
                .participantLimit(updateEventAdminRequest.getParticipantLimit())
                .requestModeration(updateEventAdminRequest.getRequestModeration())
                .state(State.valueOf(updateEventAdminRequest.getStateAction()))
                .title(updateEventAdminRequest.getTitle())
                .build();
    }

    public static EventShortDto toShortDto(Event event) {
        return EventShortDto.builder().id(event.getId())
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(UserMapper.toUserShort(event.getInitiator()))
                .eventDate(event.getEventDate().format(formatter))
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .build();
    }

    public static Event fromNewDto(NewEventDto eventDto) {
        return Event.builder().createdOn(LocalDateTime.now())
                .annotation(eventDto.getAnnotation())
                .category(Category.builder().id(eventDto.getCategory()).build())
                .description(eventDto.getDescription())
                .eventDate(LocalDateTime.parse(eventDto.getEventDate(), formatter))
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .paid(eventDto.isPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(Objects.nonNull(eventDto.getRequestModeration()) ? eventDto.getRequestModeration() : true)
                .title(eventDto.getTitle()).build();

    }
}
