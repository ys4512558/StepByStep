package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

    Optional<Challenge> findTopByUserEmailAndTitle(String email, String title);
    List<Challenge> findByUserEmailAndCompleteDateIsNotNullOrderByCompleteDateDesc(String email);
    List<Challenge> findByUserEmailAndCompleteDateIsNull(String email);

}
