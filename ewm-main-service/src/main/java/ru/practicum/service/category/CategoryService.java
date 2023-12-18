package ru.practicum.service.category;

import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;

import java.util.List;


public interface CategoryService {
    CategoryDto postCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(int catId);

    CategoryDto putCategory(int id, CategoryDto categoryDto);

    List<CategoryDto> getAllCategory(int form, int size);

    CategoryDto getCategoryById(int categoryId);
}
