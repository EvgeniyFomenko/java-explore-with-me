package ru.practicum.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class NewUserRequest {
    private String email;
    private String name;
}
