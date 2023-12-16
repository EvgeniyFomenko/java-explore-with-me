package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.SaveCompilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer>, QuerydslPredicateExecutor<Compilation> {
    Compilation save(SaveCompilation compilation);

}
