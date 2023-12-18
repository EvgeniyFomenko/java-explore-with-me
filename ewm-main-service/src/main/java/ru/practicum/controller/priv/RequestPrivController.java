package ru.practicum.controller.priv;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.service.request.RequestService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class RequestPrivController {
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

}
