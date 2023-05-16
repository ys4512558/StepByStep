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
@Table(name = "memoir")
public class Memoir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Integer idx;

    @Column
    private String email;

    @Column
    private LocalDate memoirDate;

    @Column
    private String comment;

    @Column
    private String imgUrl;
}
