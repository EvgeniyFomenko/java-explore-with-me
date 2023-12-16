package ru.practicum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.service.category.CategoryService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    //admin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/categories")
    public CategoryDto postCategories(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("Post category with newCategoryDto = {}", newCategoryDto);

        return categoryService.postCategory(newCategoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/categories/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        categoryService.deleteCategoryById(catId);
    }

    @PatchMapping("/admin/categories/{catId}")
    public CategoryDto putCategory(@PathVariable int catId, @RequestBody CategoryDto categoryDto) {
        return categoryService.putCategory(catId, categoryDto);
    }

    //public
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(required = false, defaultValue = "0") int from, @RequestParam(required = false, defaultValue = "10") int size) {
        return categoryService.getAllCategory(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategoriesById(@PathVariable int catId) {
        return categoryService.getCategoryById(catId);
    }


}
