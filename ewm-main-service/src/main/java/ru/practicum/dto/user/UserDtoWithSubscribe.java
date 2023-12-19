package ru.practicum.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDtoWithSubscribe {
    private String email;
    private int id;
    private String name;
    private List<UserShortDto> eventMaker;
}
