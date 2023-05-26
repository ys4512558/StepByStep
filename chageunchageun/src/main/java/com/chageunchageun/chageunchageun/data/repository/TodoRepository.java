package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUserEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String email, LocalDate startDate, LocalDate endDate);

    Optional<Todo> findByUserEmailAndTodoNameAndTodoDisc(String email, String todo_name, String todo_disc);
}
