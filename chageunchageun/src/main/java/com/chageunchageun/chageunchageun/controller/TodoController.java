package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.Todo.DeleteTodoDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.TodosDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.UpdateTodoDTO;
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

    /*@PostMapping(value = "save")
    public void saveRoutines(@RequestBody String todos){

        todoService.saveTodo(todos);
    }*/
    @PostMapping(value = "save")
    public void saveTodos(@RequestBody TodosDTO todos) {

        todoService.saveTodo(todos);
    }

    @PutMapping(value = "update/{email}")
    public void updateTodo(@PathVariable String email,
                           @RequestBody UpdateTodoDTO updateTodoDTO){

        todoService.updateTodo(email, updateTodoDTO);

    }


    /**
     * 투두 불러오기
     * @param email
     * @param date
     * @return
     */
    @GetMapping(value = "select")
    public ResponseEntity<TodosDTO> selectTodos(
            @RequestParam String email,
            @RequestParam String date){

        LocalDate localDate = LocalDate.parse(date);

        TodosDTO todosDTO = todoService.selectTodo(email, localDate);
        System.out.println(todosDTO);

        return ResponseEntity.status(HttpStatus.OK).body(todosDTO);
    }

    @DeleteMapping(value = "complete/{email}")
    public void completeTodo(@PathVariable String email,
                             @RequestBody DeleteTodoDTO deleteTodoDTO){

        todoService.completeTodo(email, deleteTodoDTO);

    }
}
