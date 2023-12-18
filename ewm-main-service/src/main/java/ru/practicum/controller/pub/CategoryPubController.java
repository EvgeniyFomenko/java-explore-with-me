package ru.practicum.controller.pub;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.service.category.CategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CategoryPubController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from, @RequestParam(required = false, defaultValue = "10") int size) {
        return categoryService.getAllCategory(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategoriesById(@PathVariable int catId) {
        return categoryService.getCategoryById(catId);
    }


}
