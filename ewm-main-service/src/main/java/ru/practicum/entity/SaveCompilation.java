package ru.practicum.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaveCompilation {
    private Integer[] events;
    private boolean pinned;
    private String title;
}
