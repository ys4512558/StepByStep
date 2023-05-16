package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SharedRoutine")
public class SharedRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String mbti;

    @Column
    private String category;

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
    private Integer count;

    @Column
    private LocalDate sharedDate;
}
