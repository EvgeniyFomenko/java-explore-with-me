package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.NewCompilationDto;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.service.compilation.CompilationService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CompilationController {
    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/compilations")
    public CompilationDto postCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Post compilation with compilation = {}", newCompilationDto);
        return compilationService.postCompilation(newCompilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable int compId) {
        compilationService.deleteCompilationByID(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto putCompilation(@PathVariable int compId, @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return compilationService.putCompilation(compId, updateCompilationRequest);
    }

    //public
    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(required = false) String pinned, @RequestParam(required = false, defaultValue = "0") int from, @RequestParam(required = false, defaultValue = "10") int size) {
        return compilationService.getAllCompilation(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable int compId) {
        return compilationService.getCompilationById(compId);
    }
}
