package ru.practicum.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.service.category.CategoryService;

@RestController
@AllArgsConstructor
@Slf4j
public class CategoryAdminController {
    private final CategoryService categoryService;

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


}
