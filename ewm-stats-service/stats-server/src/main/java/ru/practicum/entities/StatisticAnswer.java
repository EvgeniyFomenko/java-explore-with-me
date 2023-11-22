package ru.practicum.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticAnswer {
    private String app;
    private String uri;
    private long hits;
}
