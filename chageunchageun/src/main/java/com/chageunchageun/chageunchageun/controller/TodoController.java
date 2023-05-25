package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.RoutinesDTO;
import com.chageunchageun.chageunchageun.data.dto.TodosDTO;
import com.chageunchageun.chageunchageun.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/todo/")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping(value = "save")
    public void saveRoutines(@RequestBody String todos){

        todoService.saveTodo(todos);
    }


    /**
     * 투두 불러오기
     * @param email
     * @param date
     * @return
     */
    @GetMapping(value = "select")
    public ResponseEntity<TodosDTO> selectRoutines(
            @RequestParam String email,
            @RequestParam String date){

        LocalDate localDate = LocalDate.parse(date);

        TodosDTO todosDTO = todoService.selectTodo(email, localDate);
        System.out.println(todosDTO);

        return ResponseEntity.status(HttpStatus.OK).body(todosDTO);
    }
}
