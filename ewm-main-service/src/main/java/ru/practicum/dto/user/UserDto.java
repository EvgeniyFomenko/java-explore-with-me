package ru.practicum.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class UserDto {
    private String email;
    private int id;
    private String name;
}
