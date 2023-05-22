package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//JpaRepository<entity, primary key 타입>
public interface MemoirRepository extends JpaRepository<Memoir, Integer> {

    List<Memoir> findByUserEmail(String email);
    Optional<Memoir> findByUserEmailAndMemoirDate(String email, LocalDate memoirDate);


}
