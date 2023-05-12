package com.chageunchageun.chageunchageun.data.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharedRoutineDTO {

    /**
     * 공유된 루틴 데이터 전송 형식
     * 1. 커뮤니티에 보여질 내용들
     * 2. 여러개 (Json Array)
     */

    //mbti커뮤니티
    private String mbti;
    //공유자의 닉네임
    private String nickName;
    //카테고리(운동, 일상, 공부, 기타)
    private String categori;
    //루틴 이름
    private String routineName;
    //루틴 내용
    private String routineContent;
    //루틴 설명
    private String routineExplain;
    //시작 시간
    private String start;
    //종료 시간
    private String end;
    //공유 수
    private int count;
    //공유 시간
    private LocalDate sharedDate;

}