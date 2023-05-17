package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "user_email")
    @ToString.Exclude
    private User user;

    @Column
    private String category;

    @Column
    private String itemName;

    @Column
    private String itemDisc;

    @Column
    private String itemExplain;

    @Column
    private String start;

    @Column
    private String end;

    @Column
    private Integer count;

    @Column
    private LocalDate sharedDate;
}
