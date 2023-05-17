package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_email")
    @ToString.Exclude
    private User user;

    @Column
    private LocalDate memoirDate;

    @Column
    private String comment;

    @OneToMany(mappedBy = "memoir", fetch = FetchType.LAZY)
    private List<MemoirImg> memoirImgs;

}
