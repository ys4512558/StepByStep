package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {
    /**
     * 이메일과 요일 받음
     * 해당 유저의 루틴 전체를 리턴
     * @param email
     * @param day
     * @return
     */
    List<Routine> findByUserEmailAndDay(String email, String day);

    Optional<Routine> findByUserEmailAndItemNameAndItemDiscAndDay(String email, String item_name, String item_disc, String day);
}
