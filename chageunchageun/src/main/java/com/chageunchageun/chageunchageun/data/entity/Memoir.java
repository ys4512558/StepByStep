package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return memoirDate;
    }

    public void setDate(LocalDate MemoirDate) {
        this.memoirDate = MemoirDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
