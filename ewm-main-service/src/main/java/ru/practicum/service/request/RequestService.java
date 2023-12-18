package ru.practicum.service.request;

import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getRequestByUserId(int userId);

    ParticipationRequestDto postRequest(int userId, int eventId);

    ParticipationRequestDto rejectRequest(int userId, int requestId);

    List<ParticipationRequestDto> getRequestByUserIdAndEventId(int userId, int eventId);

    EventRequestStatusUpdateResult patchRequestByUserIdAndEventId(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
