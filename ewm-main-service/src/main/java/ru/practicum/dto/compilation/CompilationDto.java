package ru.practicum.dto.compilation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.dto.event.EventShortDto;

import java.util.List;

@Builder
@Getter
@Setter
public class CompilationDto {
    private List<EventShortDto> events;
    private int id;
    private boolean pinned;
    private String title;
}
