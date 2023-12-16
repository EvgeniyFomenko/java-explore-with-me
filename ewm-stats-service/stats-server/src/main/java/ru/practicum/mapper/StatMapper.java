package ru.practicum.mapper;

import ru.practicum.AnswerDto;
import ru.practicum.StatDto;
import ru.practicum.entities.StatisticAnswer;
import ru.practicum.entities.Statistic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatMapper {
    public static Statistic fromDto(StatDto statDto) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Statistic.builder()
                .url(statDto.getUri())
                .ip(statDto.getIp())
                .createTime(LocalDateTime.parse(statDto.getTimestamp(), dateTimeFormat))
                .app(statDto.getApp())
                .build();
    }

    public static StatDto toDto(Statistic statistic) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return StatDto.builder()
                .uri(statistic.getUrl())
                .app(statistic.getApp())
                .timestamp(statistic.getCreateTime().format(dateTimeFormat))
                .ip(statistic.getIp()).build();
    }

    public static AnswerDto toDtoAnswer(StatisticAnswer statisticAnswer) {
        return AnswerDto.builder().app(statisticAnswer.getApp()).hits((int) statisticAnswer.getHits()).uri(statisticAnswer.getUri()).build();
    }
}
