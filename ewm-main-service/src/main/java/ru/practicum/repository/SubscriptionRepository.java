package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findAllByFollowerId(int id);

    Subscription findByFollowerIdAndEventMakerId(int id, int id1);
}
