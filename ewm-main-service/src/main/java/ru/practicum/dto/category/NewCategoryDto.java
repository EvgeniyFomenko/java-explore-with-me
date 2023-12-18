package ru.practicum.dto.category;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NonNull
    private String name;
}
