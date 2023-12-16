package ru.practicum.service.request;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.entity.Event;
import ru.practicum.entity.ParticipationRequest;
import ru.practicum.entity.State;
import ru.practicum.exception.CannotRequestException;
import ru.practicum.exception.NameAlreadyException;
import ru.practicum.exception.NotFoundEventException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.ParticipationRequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getRequestByUserId(int userId) {
        return participationRequestRepository.findByRequester(userId).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto postRequest(int userId, int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException("Event not found"));
        ParticipationRequest participationRequestFind = participationRequestRepository.findByRequesterAndEvent(userId, eventId);
        if (Objects.nonNull(participationRequestFind) || event.getInitiator().getId() == userId) {
            throw new NameAlreadyException("User already set request");
        }

        if (!event.getState().equals(State.PUBLISHED.toString())) {
            throw new CannotRequestException("Request cannot ");
        }
        if (event.getParticipantLimit() == participationRequestRepository.findByEvent(eventId).size() && !event.isRequestModeration()) {
            throw new CannotRequestException("Request cannot, limit requests exhausted");
        }
        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .event(eventId)
                .requester(userId)
                .created(LocalDateTime.now())
                .status(event.getParticipantLimit() == 0 ? State.CONFIRMED.toString() : State.PENDING.toString())
                .build();
        return RequestMapper.toParticipationRequestDto(participationRequestRepository.save(participationRequest));
    }

    @Override
    public ParticipationRequestDto rejectRequest(int userId, int requestId) {
        ParticipationRequest participationRequest = participationRequestRepository.findByRequesterAndId(userId, requestId);
        participationRequest.setStatus(State.CANCELED.toString());

        return RequestMapper.toParticipationRequestDto(participationRequestRepository.save(participationRequest));
    }

    @Override
    public List<ParticipationRequestDto> getRequestByUserIdAndEventId(int userId, int eventId) {

        return participationRequestRepository.findByEvent(eventId).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult patchRequestByUserIdAndEventId(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        List<ParticipationRequest> requestList = participationRequestRepository.findByEvent(eventId);
        Event event = eventRepository.findById(eventId).get();
        List<Integer> needProcessing = eventRequestStatusUpdateRequest.getRequestIds();
        if (eventRequestStatusUpdateRequest.getStatus().equals(State.CONFIRMED.toString())) {
            long confirmed = requestList.stream().filter(e -> e.getStatus().equals(State.CONFIRMED.toString())).count();
            if ((confirmed + needProcessing.size()) > event.getParticipantLimit() || !event.isRequestModeration()) {
                throw new CannotRequestException("Limit confirms end");
            }
        }

        long confirmedButNeedProcess = requestList.stream().filter(e -> needProcessing.contains(e.getId()) && e.getStatus().contains(State.CONFIRMED.toString())).count();
        if (confirmedButNeedProcess >= 1) {
            throw new CannotRequestException("Not change status already confirmed requseter");
        }
        List<ParticipationRequestDto> participationRequestDtos = requestList.stream().filter(e -> eventRequestStatusUpdateRequest.getRequestIds().contains(e.getId())).peek(e -> e.setStatus(eventRequestStatusUpdateRequest.getStatus())).peek(participationRequestRepository::save).map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult = new EventRequestStatusUpdateResult();
        if (eventRequestStatusUpdateRequest.getStatus().equals(State.REJECTED.toString())) {
            eventRequestStatusUpdateResult.setRejectedRequests(participationRequestDtos);
        } else {
            eventRequestStatusUpdateResult.setConfirmedRequests(participationRequestDtos);
        }

        return eventRequestStatusUpdateResult;
    }
}
