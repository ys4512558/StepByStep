package com.chageunchageun.chageunchageun;

import com.chageunchageun.chageunchageun.data.entity.Memoir;
import com.chageunchageun.chageunchageun.data.entity.Routine;
import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/*

@Component
public class InitData implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoutineRepository routineRepository;
    @Autowired
    SharedRoutineRepository sharedRoutineRepository;
    @Autowired
    MemoirRepository memoirRepository;
    @Autowired
    MemoirImgRepository memoirImgRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        */
/**
         * 유저 데이터 생성
         *//*


        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setEmail(String.valueOf(i));
            user.setName(String.valueOf(i+5));
            user.setMbti("INFJ");
            user.setImgUrl("url");
            userRepository.save(user);
        }

        */
/**
         * 루틴 데이터 생성
         *//*

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                User user = userRepository.getReferenceById(String.valueOf(i));
                Routine routine = new Routine();
                routine.setUser(user);
                if(j==0){
                    routine.setDay("월");
                }
                else if(j == 1){
                    routine.setDay("화");
                }
                else {
                    routine.setDay("수");
                }
                routine.setItemName("test" + j);
                routine.setItemDisc("test" + j);
                routine.setStart(String.valueOf(i) + " : " + String.valueOf(j));
                routine.setEnd(String.valueOf(j+5) + " : " + String.valueOf(i+5));

                routineRepository.save(routine);
            }
        }

        */
/**
         * 공유 루틴 데이터 생성
         *//*

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 1; j++) {
                User user = userRepository.getReferenceById(String.valueOf(i));
                SharedRoutine sharedRoutine = new SharedRoutine();
                sharedRoutine.setUser(user);
                if(i==0){
                    sharedRoutine.setCategory("운동");
                }
                else if(i == 1){
                    sharedRoutine.setCategory("일상");
                }
                else {
                    sharedRoutine.setCategory("공부");
                }
                sharedRoutine.setItemName("test" + j);
                sharedRoutine.setItemDisc("test" + j);
                sharedRoutine.setItemExplain("testtestestestset");
                sharedRoutine.setStart(String.valueOf(i) + " : " + String.valueOf(j));
                sharedRoutine.setEnd(String.valueOf(j+5) + " : " + String.valueOf(i+5));
                sharedRoutine.setCount(0);
                sharedRoutine.setSharedDate(LocalDate.now());

                sharedRoutineRepository.save(sharedRoutine);
            }
        }

        */
/**
         *  회고 데이터 생성
         *//*

        */
/*
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                User user = userRepository.getReferenceById(String.valueOf(i));
                Memoir memoir = new Memoir();
                memoir.setUser(user);
                String date = "2023-05-1" + j;
                memoir.setMemoirDate(LocalDate.parse(date));

                memoirRepository.save(memoir);
            }
        }*//*


    }
}
*/
