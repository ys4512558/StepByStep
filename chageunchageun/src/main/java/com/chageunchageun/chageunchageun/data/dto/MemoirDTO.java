package com.chageunchageun.chageunchageun.data.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

public class MemoirDTO {

    private String email;

    private LocalDate date;

    private String comment;

    private MultipartFile img;

    public MemoirDTO() {
    }

    public MemoirDTO(String email, LocalDate date, String comment, MultipartFile img) {
        this.email = email;
        this.date = date;
        this.comment = comment;
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
