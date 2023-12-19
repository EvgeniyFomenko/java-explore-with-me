package ru.practicum.controller.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.user.UpdateEventUserRequest;
import ru.practicum.dto.user.UserDtoWithSubscribe;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.entity.State;
import ru.practicum.service.event.EventServiceImpl;
import ru.practicum.service.request.RequestService;
import ru.practicum.service.users.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class EventPrivController {
    private final EventServiceImpl eventService;
    private final RequestService requestService;
    private final UserService userService;

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEvent(@PathVariable int userId, @RequestParam(defaultValue = "0") Integer from, @RequestParam(required = false,
            defaultValue = "10") Integer size) {
        log.info("Get evetnts by user with userId = {} from = {} size = {}", userId, from, size);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/events")
    public EventFullDto postEvent(@PathVariable int userId, @Valid @RequestBody NewEventDto eventDto) {
        log.info("Post event with userId = {}, eventDto = {}", userId, eventDto);
        return eventService.postEvent(userId, eventDto);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventById(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.getEventByIdAndUserId(eventId, userId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto putEventById(@PathVariable int userId, @PathVariable int eventId, @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Event path with event = {}", updateEventUserRequest);
        return eventService.putEventByIdUserAndIdEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestByUserAndEvent(@PathVariable int userId, @PathVariable int eventId) {
        log.info("Get request with userId = {}, requestId = {}", userId, eventId);
        return requestService.getRequestByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequestByUserAndEvent(@PathVariable int userId,
                                                                     @PathVariable int eventId,
                                                                     @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Patch request with userId = {}, requestId = {}, eventUpdateBody = {}", userId, eventId, eventRequestStatusUpdateRequest);
        return requestService.patchRequestByUserIdAndEventId(userId, eventId, eventRequestStatusUpdateRequest);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/{followerId}/subscribe/{eventMakerId}")
    public UserDtoWithSubscribe subscribeToEventMaker(@PathVariable int followerId, @PathVariable int eventMakerId) {
        return userService.subscribeToEventMaker(followerId, eventMakerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{followerId}/subscribe/{eventMakerId}/delete")
    public void deleteSubscribe(@PathVariable int followerId, @PathVariable int eventMakerId) {
        userService.deleteEventMakerFromSubscribe(followerId, eventMakerId);
    }

    @GetMapping("/events/user/{followerId}/subscribe")
    public List<EventFullDto> getEventSubscribesByFollowerId(@PathVariable int followerId, @RequestParam(defaultValue = "PUBLISH_EVENT") String state) {
        return eventService.getEventSubscribesByFollowerId(followerId, State.valueOf(state));
    }

    @GetMapping("/user/{followerId}/subscribe")
    public List<UserShortDto> getUserSubscribesByFollowerId(@PathVariable int followerId) {
        return userService.getUserSubscribesByFollowerId(followerId);
    }
}
