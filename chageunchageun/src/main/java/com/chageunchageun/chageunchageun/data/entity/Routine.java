package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routine")
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    Integer idx;

    @ManyToOne
    @JoinColumn(name = "user_email")
    @ToString.Exclude
    private User user;

    @Column
    private String day;

    @Column
    private String itemName;

    @Column
    private String itemDisc;

    @Column
    private String start;

    @Column
    private String end;

    @Column
    private int like_cnt;

    public Routine(User user, String day, String itemName, String itemDisc, String start, String end) {
        this.user = user;
        this.day = day;
        this.itemName = itemName;
        this.itemDisc = itemDisc;
        this.start = start;
        this.end = end;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDisc(String itemDisc) {
        this.itemDisc = itemDisc;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }
}
