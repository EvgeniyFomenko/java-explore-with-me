package ru.practicum.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class NewCompilationDto {
    private Integer[] events;
    private boolean pinned;
    private String title;
}
