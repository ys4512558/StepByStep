package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedRoutineRepository extends JpaRepository<SharedRoutine, Integer> {
    List<SharedRoutine> findByCategory(String category);

    @Query(value = "SELECT u.mbti, u.email, u.name, sr FROM User as u join SharedRoutine as sr on u.email = sr.user.email where mbti = :mbti")
    List<Object[]> getSharedRoutinesWithUserMbti(@Param(value = "mbti") String mbti);
}
