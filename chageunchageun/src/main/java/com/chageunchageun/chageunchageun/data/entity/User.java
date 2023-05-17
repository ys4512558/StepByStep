package com.chageunchageun.chageunchageun.data.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Routine> routines;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Todo> todos;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SharedRoutine> sharedRoutines;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Memoir> memoirs;

}
