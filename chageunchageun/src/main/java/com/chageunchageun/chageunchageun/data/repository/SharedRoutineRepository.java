package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedRoutineRepository extends JpaRepository<SharedRoutine, Integer> {
    List<SharedRoutine> findByCategory(String category);
}
