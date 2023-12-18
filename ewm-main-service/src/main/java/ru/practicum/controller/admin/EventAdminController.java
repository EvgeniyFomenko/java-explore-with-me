package ru.practicum.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.UpdateEventAdminRequest;
import ru.practicum.service.event.EventServiceImpl;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class EventAdminController {
    private final EventServiceImpl eventService;

    @GetMapping("/admin/events")
    public List<EventFullDto> getEvent(@RequestParam(required = false) Integer[] users, @RequestParam(required = false) String[] states, @RequestParam(required = false) Integer[] categories, @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10", required = false) int size) {
        log.info("Get event with users = {}, states = {}, categories = {},rangeStart = {}, rangeEnd = {}, from = {}, size = {}", Arrays.toString(users), Arrays.toString(states), Arrays.toString(categories), rangeStart, rangeEnd, from, size);
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventAdminRequest(@PathVariable int eventId, @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("patch event with eventId ={} updateEventAdminRequest = {}", eventId, updateEventAdminRequest);
        return eventService.updateEventAdmin(updateEventAdminRequest, eventId);
    }

}
