package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharedRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String mbti;

    @Column
    private String nickName;

    @Column
    private String categori;

    @Column
    private String routineName;

    @Column
    private String routineContent;

    @Column
    private String routineExplain;

    @Column
    private String start;

    @Column
    private String end;

    @Column
    private int count;

    @Column
    private LocalDate sharedDate;
}
