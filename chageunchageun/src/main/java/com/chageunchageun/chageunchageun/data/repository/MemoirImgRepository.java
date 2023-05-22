package com.chageunchageun.chageunchageun.data.repository;

import com.chageunchageun.chageunchageun.data.entity.MemoirImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoirImgRepository extends JpaRepository<MemoirImg, Integer> {

    List<MemoirImg> findByMemoirIdx(Integer memoir_idx);

    Optional<MemoirImg> findByImgUrlLike(String date);
    Optional<MemoirImg> findByImgUrlContains(String date);


}
