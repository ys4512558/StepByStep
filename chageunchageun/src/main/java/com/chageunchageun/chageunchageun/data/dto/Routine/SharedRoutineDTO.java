package com.chageunchageun.chageunchageun.data.dto.Routine;

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

    private Integer idx;
    //루틴 이름
    private String itemName;
    //루틴 내용
    private String itemDisc;
    //시작 시간
    private String start;
    //종료 시간
    private String end;
    //공유 수
    private int like_cnt;

}
