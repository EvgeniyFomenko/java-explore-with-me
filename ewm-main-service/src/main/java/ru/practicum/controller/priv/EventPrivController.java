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
import ru.practicum.service.event.EventServiceImpl;
import ru.practicum.service.request.RequestService;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class EventPrivController {
    private final EventServiceImpl eventService;
    private final RequestService requestService;

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEvent(@PathVariable int userId, @RequestParam(defaultValue = "0") Integer from, @RequestParam(required = false,
            defaultValue = "10") Integer size) {
        EventPrivController.log.info("Get evetnts by user with userId = {} from = {} size = {}", userId, from, size);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/events")
    public EventFullDto postEvent(@PathVariable int userId, @Valid @RequestBody NewEventDto eventDto) {
        EventPrivController.log.info("Post event with userId = {}, eventDto = {}", userId, eventDto);
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
        EventPrivController.log.info("Get request with userId = {}, requestId = {}", userId, eventId);
        return requestService.getRequestByUserIdAndEventId(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequestByUserAndEvent(@PathVariable int userId,
                                                                     @PathVariable int eventId,
                                                                     @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        EventPrivController.log.info("Patch request with userId = {}, requestId = {}, eventUpdateBody = {}", userId, eventId, eventRequestStatusUpdateRequest);
        return requestService.patchRequestByUserIdAndEventId(userId, eventId, eventRequestStatusUpdateRequest);

    }

}
