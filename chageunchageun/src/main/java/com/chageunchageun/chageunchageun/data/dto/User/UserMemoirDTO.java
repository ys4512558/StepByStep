package com.chageunchageun.chageunchageun.data.dto.User;

import com.chageunchageun.chageunchageun.data.dto.Memoir.MemoirPreviewDTO;
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

    private String level;

    private String exp;

    private List<MemoirPreviewDTO> previewList;


}
