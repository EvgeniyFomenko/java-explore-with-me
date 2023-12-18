package ru.practicum.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@ToString
public class NewUserRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    @Size(min = 6, max = 254)
    private String email;
    @Size(min = 2, max = 250)
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
}
