package ru.practicum.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.NewCompilationDto;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.service.compilation.CompilationService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
public class CompilationAdminController {
    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/compilations")
    public CompilationDto postCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Post compilation with compilation = {}", newCompilationDto);
        return compilationService.postCompilation(newCompilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable int compId) {
        compilationService.deleteCompilationByID(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto putCompilation(@PathVariable int compId, @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return compilationService.putCompilation(compId, updateCompilationRequest);
    }

}
