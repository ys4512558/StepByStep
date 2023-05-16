package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    Integer idx;

    //외래키 지정
    @ManyToOne
    @JoinColumn(name = "user_email")
    @ToString.Exclude
    private User user;

    @Column
    private String todoName;

    @Column
    private boolean repeat;

    @Column
    private String todoContent;

    @Column
    private String startDay;

    @Column
    private String endDay;
}
