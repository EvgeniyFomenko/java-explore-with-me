package ru.practicum.service.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.entity.Category;
import ru.practicum.entity.Event;
import ru.practicum.exception.CategoryException;
import ru.practicum.exception.EntityNotCanDeleteException;
import ru.practicum.exception.NameAlreadyException;
import ru.practicum.exception.NotFoundCategoryException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.fromNewCategoryDto(newCategoryDto);
        validate(category);

        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    public void deleteCategoryById(int catId) {
        List<Event> eventsFind = eventRepository.findByCategoryId(catId);
        if (Objects.nonNull(eventsFind) && eventsFind.size() > 0) {
            throw new EntityNotCanDeleteException("Category has event");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto putCategory(int id, CategoryDto categoryDto) {
        Category category = CategoryMapper.fromCategoryDto(categoryDto);
        category.setId(id);
        validate(category);


        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getAllCategory(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id");
        return categoryRepository.findAll(pageRequest).stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundCategoryException("Категория не найдена")));
    }

    private void validate(Category category) {

        if (Objects.isNull(category.getName()) || category.getName().isBlank() || category.getName().isEmpty()) {
            throw new CategoryException("Category name not can empty or blank");
        }

        if (category.getName().length() > 50) {
            throw new CategoryException("Category name not cant be bigger than 50  ");
        }

        Category categoryFind = categoryRepository.findByName(category.getName());
        if (Objects.nonNull(categoryFind) && categoryFind.getId() != category.getId()) {
            throw new NameAlreadyException("Category name already exists");
        }
    }


}
