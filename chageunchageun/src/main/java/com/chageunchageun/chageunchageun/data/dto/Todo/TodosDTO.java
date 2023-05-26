package com.chageunchageun.chageunchageun.data.dto.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodosDTO {
    /**
     * 여러 루틴을 전송하기 위한 RoutinesDTO
     * JsonArray로 전송하기 위해
     */
    private List<TodoDTO> todos;

    private String email;
}
