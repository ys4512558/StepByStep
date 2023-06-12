package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    Optional<Routine> findTopByUserEmailAndItemNameAndItemDiscAndDay(String email, String item_name, String item_disc, String day);

    //@Query(value = "SELECT u.mbti, u.email, u.name, r FROM User as u join Routine as r on u.email = r.user.email where u.mbti = :mbti")
    @Query(value = "SELECT r FROM User as u join Routine as r on u.email = r.user.email where u.mbti = :mbti")
    List<Object[]> getSharedRoutinesWithUserMbti(@Param(value = "mbti") String mbti);

    @Query(value = "SELECT r.idx FROM User as u join Routine as r on u.email = r.user.email where u.mbti = :mbti")
    List<Integer> getIdxWithUserMbti(@Param(value = "mbti") String mbti);

    @Query(value = "SELECT r.idx FROM User as u join Routine as r on u.email = r.user.email where u.mbti = :mbti and u.email != :email")
    List<Integer> getIdxWithUserMbtiAndEmail(@Param(value = "mbti") String mbti, @Param(value = "email") String email);


}
