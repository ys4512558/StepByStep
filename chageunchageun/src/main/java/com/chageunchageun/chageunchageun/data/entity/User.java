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
    String email;

    @Column
    String name;

    @Column
    String imgUrl;

    @Column
    String mbti;

    @Column
    String userPath;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Routine> routines;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Todo> todos;
}
