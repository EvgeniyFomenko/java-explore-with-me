package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.AnswerDto;
import ru.practicum.ParamStatDto;
import ru.practicum.StatDto;
import ru.practicum.entities.Statistic;
import ru.practicum.entities.StatisticAnswer;
import ru.practicum.exception.DateStatQueryException;
import ru.practicum.mapper.StatMapper;
import ru.practicum.storage.StatStorage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatServiceImpl implements StatService {
    private final StatStorage statStorage;

    public StatDto saveStat(StatDto statDto) {
        Statistic statistic = StatMapper.fromDto(statDto);

        return StatMapper.toDto(statStorage.save(statistic));
    }

    public List<AnswerDto> getStatList(ParamStatDto paramStatDto) {
        log.info("paramStatDto with start = {}, end = {}, uri = {} ", paramStatDto.getStart(), paramStatDto.getEnd(), Arrays.toString(paramStatDto.getUris()));
        if (paramStatDto.getStart().isAfter(paramStatDto.getEnd())) {
            throw new DateStatQueryException("Start and end date wrong");
        }
        List<StatisticAnswer> statisticList = getStatisticList(paramStatDto);

        return statisticList
                .stream()
                .map(StatMapper::toDtoAnswer)
                .collect(Collectors.toList());
    }

    private List<StatisticAnswer> getStatisticList(ParamStatDto paramStatDto) {
        if (Boolean.parseBoolean(paramStatDto.getUnique())) {
            if (Objects.isNull(paramStatDto.getUris()[0])) {
                return statStorage.searchDistinctByStartEnd(paramStatDto.getStart(),
                        paramStatDto.getEnd());
            } else {
                return statStorage.searchDistinctByUris(paramStatDto.getUris(),
                        paramStatDto.getStart(),
                        paramStatDto.getEnd());
            }
        } else {
            if (Objects.isNull(paramStatDto.getUris()[0])) {
                return statStorage.searchByStartEnd(paramStatDto.getStart(),
                        paramStatDto.getEnd());
            } else {
                return statStorage.searchByUris(paramStatDto.getUris(),
                        paramStatDto.getStart(),
                        paramStatDto.getEnd());
            }
        }

    }
}
