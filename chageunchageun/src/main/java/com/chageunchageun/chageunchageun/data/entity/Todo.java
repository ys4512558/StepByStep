package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    Integer idx;

    @ManyToOne
    @JoinColumn(name = "user_email")
    @ToString.Exclude
    private User user;

    @Column
    private String todoName;

    @Column
    private String todoDisc;

    @Column
    private Boolean oneOff;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    public Todo(User user, String todoName, String todoDisc, Boolean oneOff, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.todoName = todoName;
        this.todoDisc = todoDisc;
        this.oneOff = oneOff;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public void setTodoDisc(String todoDisc) {
        this.todoDisc = todoDisc;
    }

    public void setOneOff(Boolean oneOff) {
        this.oneOff = oneOff;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
