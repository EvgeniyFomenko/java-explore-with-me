package ru.practicum.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class CategoryDto {
    private int id;
    private String name;
}
