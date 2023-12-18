package ru.practicum.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.user.UserShortDto;

@Getter
@Setter
@Builder
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String eventDate;
    private int id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private int views;
}
