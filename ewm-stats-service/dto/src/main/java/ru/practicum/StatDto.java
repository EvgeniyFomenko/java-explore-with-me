package ru.practicum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class StatDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
