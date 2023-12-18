package ru.practicum.controller.pub;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.StatDto;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.service.StatsService;
import ru.practicum.service.event.EventServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class EventPubController {
    private final EventServiceImpl eventService;
    private final StatsService statsService;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(@RequestParam(required = false) String text, @RequestParam(required = false) Integer[] categories, @RequestParam(required = false) Boolean paid, @RequestParam(required = false) String rangeStart, @RequestParam(required = false) String rangeEnd, @RequestParam(defaultValue = "false", required = false) Boolean onlyAvailable, @RequestParam(defaultValue = "EVENT_DATE", required = false) String sort, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10", required = false) Integer size, HttpServletRequest request) {
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
