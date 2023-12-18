package ru.practicum.dto.compilation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateCompilationRequest {
    private Integer[] events;
    private Boolean pinned;
    @Size(max = 50)
    private String title;
}
