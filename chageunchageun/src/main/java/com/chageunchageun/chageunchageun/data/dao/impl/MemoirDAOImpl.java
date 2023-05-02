package com.chageunchageun.chageunchageun.data.dao.impl;

import com.chageunchageun.chageunchageun.data.dao.MemoirDAO;
import com.chageunchageun.chageunchageun.data.entity.Memoir;
import com.chageunchageun.chageunchageun.data.repository.MemoirRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MemoirDAOImpl implements MemoirDAO {

    //로그
    private final Logger LOGGER = LoggerFactory.getLogger(MemoirDAOImpl.class);
    private final MemoirRepository memoirRepository;
    @Autowired
    public MemoirDAOImpl(MemoirRepository memoirRepository) {
        this.memoirRepository = memoirRepository;
    }

    @Override
    public Memoir insertMemoir(Memoir memoir) {
        Memoir savedMemoir = memoirRepository.save(memoir);
        return savedMemoir;
    }

    /**
     * email, date를 받아서 쿼리 생성
     * 결과값 받아오기
     * 새로 다 짜야함
     * @param email
     * @param date
     * @return
     */
    @Override
    public Memoir selectMemoir(String email, LocalDate date) {
        Memoir selecedMemoir = memoirRepository.getReferenceById(email);

        return selecedMemoir;
    }

    @Override
    public Memoir updateMemoir(String email, String commend) {
        return null;
    }

    @Override
    public void deleteMemoir(String email, String date) {

    }
}
