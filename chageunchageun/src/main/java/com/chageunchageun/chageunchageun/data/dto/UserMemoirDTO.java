package com.chageunchageun.chageunchageun.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMemoirDTO {
    private String email;

    private String name;

    private String imgUrl;

    private String mbti;

    private List<MemoirPreviewDTO> previewList;


}
