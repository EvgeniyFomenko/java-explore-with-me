package ru.practicum.controller.pub;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.service.compilation.CompilationService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CompilationPubController {
    private final CompilationService compilationService;

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(required = false) String pinned, @RequestParam(defaultValue = "0") int from, @RequestParam(required = false, defaultValue = "10") int size) {
        return compilationService.getAllCompilation(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable int compId) {
        return compilationService.getCompilationById(compId);
    }
}
