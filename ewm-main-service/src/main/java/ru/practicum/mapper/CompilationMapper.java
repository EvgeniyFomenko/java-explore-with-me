package ru.practicum.mapper;

import ru.practicum.dto.category.NewCompilationDto;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.entity.SaveCompilation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static SaveCompilation toSaveCompilation(NewCompilationDto newCompilationDto) {
        return SaveCompilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.isPinned())
                .events(newCompilationDto.getEvents())
                .build();


    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(Objects.nonNull(compilation.getEvent()) ? compilation.getEvent().stream().map(EventMapper::toShortDto).collect(Collectors.toList()) : new ArrayList<>())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto saveCompilation) {
        return Compilation.builder().title(saveCompilation.getTitle())
                .pinned(saveCompilation.isPinned())
                .event(Arrays.stream(saveCompilation.getEvents()).map(e -> Event.builder().id(e).build()).collect(Collectors.toList()))
                .build();
    }
}
