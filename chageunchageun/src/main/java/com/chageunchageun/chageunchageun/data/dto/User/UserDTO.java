package com.chageunchageun.chageunchageun.data.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String email;

    private String name;

    private String imgUrl;

    private String mbti;

    private int level;

    private int exp;

}
