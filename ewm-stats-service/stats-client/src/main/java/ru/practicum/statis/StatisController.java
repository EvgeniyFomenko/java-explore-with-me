package ru.practicum.statis;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatDto;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class StatisController {
    private final StatisService statisService;

    @PostMapping("/hit")
    public ResponseEntity<Object> postStatistics(@RequestBody StatDto statDto) {
        return statisService.postStatistics(statDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStatistic(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end, @RequestParam(required = false) String[] uris, @RequestParam(defaultValue = "false", required = false) String uniq) {
        return statisService.getStatistic(start, end, uris, uniq);
    }
}
