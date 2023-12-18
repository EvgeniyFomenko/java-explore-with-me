package ru.practicum.service.compilation;

import ru.practicum.dto.category.NewCompilationDto;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);

    List<CompilationDto> getAllCompilation(String pinned, int from, int size);

    void deleteCompilationByID(int id);

    CompilationDto getCompilationById(int id);

    CompilationDto putCompilation(int id, UpdateCompilationRequest updateCompilationRequest);
}
