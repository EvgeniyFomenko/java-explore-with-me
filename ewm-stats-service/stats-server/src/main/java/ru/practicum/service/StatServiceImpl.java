package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.AnswerDto;
import ru.practicum.ParamStatDto;
import ru.practicum.StatDto;
import ru.practicum.entities.StatisticAnswer;
import ru.practicum.entities.StatisticEntity;
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
        StatisticEntity statisticEntity = StatMapper.fromDto(statDto);

        return StatMapper.toDto(statStorage.save(statisticEntity));
    }

    public List<AnswerDto> getStatList(ParamStatDto paramStatDto) {
        log.info("paramStatDto with start = {}, end = {}, uri = {} ", paramStatDto.getStart(), paramStatDto.getEnd(), Arrays.toString(paramStatDto.getUris()));
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
