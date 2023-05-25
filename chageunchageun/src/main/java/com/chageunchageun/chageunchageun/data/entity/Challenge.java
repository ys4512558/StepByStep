package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenge")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Integer idx;

    @Column
    private String title;

    @Column
    private LocalDate completeDate;

    @ManyToOne
    @JoinColumn(name = "user_email")
    @ToString.Exclude
    private User user;

    public Challenge(User user, String title){
        this.user = user;
        this.title = title;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }
}
