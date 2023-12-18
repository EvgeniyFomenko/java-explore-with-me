package ru.practicum.mapper;

import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.entity.ParticipationRequest;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .requester(participationRequest.getRequester())
                .id(participationRequest.getId())
                .event(participationRequest.getEvent())
                .status(participationRequest.getStatus().toString())
                .created(participationRequest.getCreated().toString())
                .build();
    }
}
