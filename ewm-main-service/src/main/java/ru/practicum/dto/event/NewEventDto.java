package ru.practicum.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.entity.Location;

@Getter
@Setter
@ToString
public class NewEventDto {
    private String annotation;
    private int category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private String title;

}
