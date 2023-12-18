package ru.practicum.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.entity.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class NewEventDto {
    @Size(min = 20, max = 2000)
    @NotNull
    @NotBlank
    @NotEmpty
    private String annotation;
    private int category;
    @Size(min = 20, max = 7000)
    @NotNull
    @NotBlank
    @NotEmpty
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;

}
