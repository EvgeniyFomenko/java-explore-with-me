package ru.practicum.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ParticipationRequestDto {
    private int id;
    private String created;
    private int event;
    private int requester;
    private String status;
}
