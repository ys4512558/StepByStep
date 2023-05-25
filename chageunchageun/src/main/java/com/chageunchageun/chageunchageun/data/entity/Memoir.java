package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
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
    private String title;

    @Column
    private String comment;

    @Column
    private String mood;

    @OneToMany(mappedBy = "memoir", fetch = FetchType.LAZY)
    private List<MemoirImg> memoirImgs;

    public Memoir(User user, LocalDate memoirDate) {
        this.user = user;
        this.memoirDate = memoirDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setMemoirImgs(List<MemoirImg> memoirImgs) {
        this.memoirImgs = memoirImgs;
    }
}
