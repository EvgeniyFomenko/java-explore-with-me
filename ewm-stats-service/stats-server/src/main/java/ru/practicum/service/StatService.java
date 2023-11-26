package ru.practicum.service;

import ru.practicum.AnswerDto;
import ru.practicum.ParamStatDto;
import ru.practicum.StatDto;

import java.util.List;

public interface StatService {
    StatDto saveStat(StatDto statDto);

    List<AnswerDto> getStatList(ParamStatDto paramStatDto);
}
