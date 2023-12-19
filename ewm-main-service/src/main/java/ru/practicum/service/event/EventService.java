package ru.practicum.service.event;

import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.dto.user.UpdateEventUserRequest;
import ru.practicum.entity.State;

import java.util.List;

public interface EventService {


    List<EventFullDto> getEvents(Integer[] users, String[] states, Integer[] categories, String rangeStart, String rangeEnd, int from, int size);

    EventFullDto updateEventAdmin(UpdateEventAdminRequest updateEventAdminRequest, int eventId);

    List<EventShortDto> getEventsByUserId(int userId, int from, int size);

    EventFullDto postEvent(int userId, NewEventDto eventDto);

    EventFullDto getEventByIdAndUserId(int userId, int eventId);

    EventFullDto putEventByIdUserAndIdEvent(int userId, int eventId, UpdateEventUserRequest updateEventAdminRequest);

    List<EventShortDto> getAllEvents(String test, Integer[] categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventFullDto getEventById(int id);

    List<EventFullDto> getEventSubscribesByFollowerId(int followerId, State state);

    int getViews(int id);
}
