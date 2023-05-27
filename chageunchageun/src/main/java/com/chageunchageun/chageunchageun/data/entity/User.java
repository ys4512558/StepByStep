package com.chageunchageun.chageunchageun.data.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {

    @Id
    private String email;

    @Column
    private String name;

    @Column
    private String imgUrl;

    @Column
    private String mbti;

    @Column
    private String userPath;

    @Column
    @ColumnDefault(value = "1")
    private int level;

    @Column
    @ColumnDefault(value = "0.0")
    private float exp;

    public User(String email, String name, String imgUrl, String mbti, String userPath, int level, float exp) {
        this.email = email;
        this.name = name;
        this.imgUrl = imgUrl;
        this.mbti = mbti;
        this.userPath = userPath;
        this.level = level;
        this.exp = exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Routine> routines;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Todo> todos;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SharedRoutine> sharedRoutines;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Memoir> memoirs;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Challenge> challenges;


    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public void setSharedRoutines(List<SharedRoutine> sharedRoutines) {
        this.sharedRoutines = sharedRoutines;
    }

    public void setMemoirs(List<Memoir> memoirs) {
        this.memoirs = memoirs;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }
}
