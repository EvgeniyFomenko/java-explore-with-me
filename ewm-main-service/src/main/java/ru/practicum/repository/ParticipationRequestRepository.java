package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    List<ParticipationRequest> findByRequester(int userId);

    ParticipationRequest findByRequesterAndId(int userId, int requestId);

    ParticipationRequest findByRequesterAndEvent(int userId, int eventId);

    List<ParticipationRequest> findByEvent(int eventId);
}
