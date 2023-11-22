package ru.practicum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerDto {
    private String app;
    private String uri;
    private int hits;
}
