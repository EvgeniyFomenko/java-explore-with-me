package ru.practicum.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.entity.Location;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    @Size(min = 3, max = 120)
    private String title;
}
