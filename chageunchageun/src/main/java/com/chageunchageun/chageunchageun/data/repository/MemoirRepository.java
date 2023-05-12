package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

//JpaRepository<entity, primary key 타입>
public interface MemoirRepository extends JpaRepository<Memoir, Integer> {

    Memoir findByEmailAndMemoirDate(String email, LocalDate memoirDate);


}
