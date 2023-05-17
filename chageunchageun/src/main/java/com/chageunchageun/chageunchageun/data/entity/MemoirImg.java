package com.chageunchageun.chageunchageun.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "memoir_img")
public class MemoirImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    Integer idx;

    @ManyToOne
    @JoinColumn(name = "memoir_idx")
    @ToString.Exclude
    private Memoir memoir;

    @Column
    private String imgUrl;

}
