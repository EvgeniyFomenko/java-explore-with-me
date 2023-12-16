package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.dto.user.UpdateEventUserRequest;
import ru.practicum.service.event.EventServiceImpl;
import ru.practicum.service.StatsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class EventController {
    private final EventServiceImpl eventService;
    private final StatsService statsService;

    @GetMapping("/admin/events")
    public List<EventFullDto> getEvent(@RequestParam(required = false) Integer[] users, @RequestParam(required = false) String[] states, @RequestParam(required = false) Integer[] categories, @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd, @RequestParam(defaultValue = "0", required = false) int from, @RequestParam(defaultValue = "10", required = false) int size) {
        log.info("Get event with users = {}, states = {}, categories = {},rangeStart = {}, rangeEnd = {}, from = {}, size = {}", Arrays.toString(users), Arrays.toString(states), Arrays.toString(categories), rangeStart, rangeEnd, from, size);
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventAdminRequest(@PathVariable int eventId, @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("patch event with eventId ={} updateEventAdminRequest = {}", eventId, updateEventAdminRequest);
        return eventService.updateEventAdmin(updateEventAdminRequest, eventId);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEvent(@PathVariable int userId, @RequestParam(required = false, defaultValue = "0") Integer from, @RequestParam(required = false,
            defaultValue = "10") Integer size) {
        log.info("Get evetnts by user with userId = {} from = {} size = {}", userId, from, size);
        return eventService.getEventsByUserId(userId, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/events")
    public EventFullDto postEvent(@PathVariable int userId, @RequestBody NewEventDto eventDto) {
        log.info("Post event with userId = {}, eventDto = {}", userId, eventDto);
        return eventService.postEvent(userId, eventDto);
    }

    //private
    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventById(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.getEventByIdAndUserId(eventId, userId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto putEventById(@PathVariable int userId, @PathVariable int eventId, @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.putEventByIdUserAndIdEvent(userId, eventId, updateEventUserRequest);
    }

    //public
    @GetMapping("/events")
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text, @RequestParam(required = false) Integer[] categories, @RequestParam(required = false) Boolean paid, @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd, @RequestParam(defaultValue = "false", required = false) Boolean onlyAvailable, @RequestParam(defaultValue = "EVENT_DATE", required = false) String sort, @RequestParam(defaultValue = "0", required = false) Integer from, @RequestParam(defaultValue = "10", required = false) Integer size, HttpServletRequest request) {
        log.info("Get events with text ={} categories = {} paid = {} rangeStart = {} rangeEnd = {} onlyAvailable = {} sort = {}", text, Arrays.toString(categories), paid, rangeStart, rangeEnd, onlyAvailable, sort);
        StatDto statDto = StatDto.builder().uri(request.getRequestURI()).ip(request.getRemoteAddr()).app("events").timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString()).build();
        statsService.postStatistics(statDto);
        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEventsById(@PathVariable int id, HttpServletRequest request) {
        StatDto statDto = StatDto.builder().uri(request.getRequestURI()).ip(request.getRemoteAddr()).app("events").timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString()).build();
        statsService.postStatistics(statDto);
        return eventService.getEventById(id);
    }

}
