package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.AnswerDto;
import ru.practicum.ParamStatDto;
import ru.practicum.StatDto;
import ru.practicum.service.StatServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {
    private final StatServiceImpl statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatDto postHit(@RequestBody StatDto statDto) {
        log.info("Enpoint hit with statDto = {}", statDto.toString());
        return statService.saveStat(statDto);
    }

    @GetMapping("/stats")
    public List<AnswerDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end, @RequestParam(required = false) String[] uris, @RequestParam(defaultValue = "false", required = false) String unique) {
        log.info("Endpoint stats with uris = {}, start = {}, end = {}, uniq = {}", Arrays.toString(uris), start, end, unique);
        ParamStatDto paramStatDto = ParamStatDto.builder().start(start).unique(unique).end(end).uris(Objects.isNull(uris) ? new String[1] : uris).build();

        return statService.getStatList(paramStatDto);
    }


}
