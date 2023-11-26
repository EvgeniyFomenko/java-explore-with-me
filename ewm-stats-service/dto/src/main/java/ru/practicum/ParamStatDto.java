package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ParamStatDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private String[] uris;
    private String unique;
}
