package ru.practicum.dto.compilation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateCompilationRequest {
    private Integer[] events;
    private Boolean pinned;
    private String title;
}
