package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.Memoir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface MemoirRepository extends JpaRepository<Memoir, String> {

    Memoir findByEmailAndMemoirDate(String email, LocalDate memoirDate);


}
