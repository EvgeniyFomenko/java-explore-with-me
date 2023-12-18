package ru.practicum.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Builder
@Getter
@Setter
@ToString
public class NewCompilationDto {
    private Integer[] events;
    private boolean pinned;
    @NotBlank
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String title;
}
