package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {

    Page<Event> findByInitiatorId(int userId, PageRequest id);

    Optional<Event> findByInitiatorIdAndId(int userId, int eventId);


    Optional<Event> findByIdAndInitiatorId(int eventId, int userId);

    Optional<Event> findByIdAndState(int id, String state);

    List<Event> findByCategoryId(int catId);


}
