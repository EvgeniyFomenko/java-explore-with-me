package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {

    User findByName(String name);
}
