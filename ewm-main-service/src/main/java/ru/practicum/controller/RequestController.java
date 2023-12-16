package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.service.request.RequestService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getRequest(@PathVariable int userId) {
        log.info("Get request with userId = {}", userId);
        return requestService.getRequestByUserId(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto postRequest(@PathVariable int userId, @RequestParam int eventId) {
        log.info("Post request with userId = {}, eventId = {}", userId, eventId);
        return requestService.postRequest(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto patchRejectRequest(@PathVariable int userId, @PathVariable int requestId) {
        log.info("Patch cancel request with userId = {}, requestId = {}", userId, requestId);
        return requestService.rejectRequest(userId, requestId);
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
}
