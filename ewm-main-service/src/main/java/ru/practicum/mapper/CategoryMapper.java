package ru.practicum.mapper;

import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.entity.Category;

public class CategoryMapper {
    public static Category fromCategoryDto(CategoryDto categoryDto) {
        return Category.builder().name(categoryDto.getName()).build();
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder().name(category.getName()).id(category.getId()).build();
    }

    public static NewCategoryDto toNewCategoryDto(Category category) {
        return NewCategoryDto.builder().name(category.getName()).build();
    }

    public static Category fromNewCategoryDto(NewCategoryDto newCategoryDto) {
        return Category.builder().name(newCategoryDto.getName()).build();
    }
}
