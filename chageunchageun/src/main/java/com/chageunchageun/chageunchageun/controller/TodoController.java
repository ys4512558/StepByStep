package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.Todo.DeleteTodoDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.TodoDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.UpdateDateDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.UpdateTodoDTO;
import com.chageunchageun.chageunchageun.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/todo/")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /*@PostMapping(value = "save")
    public void saveRoutines(@RequestBody String todos){

        todoService.saveTodo(todos);
    }*/
    @PostMapping(value = "save/{email}")
    public ResponseEntity<HttpStatus> saveTodos(@RequestBody List<TodoDTO> todoDTOS, @PathVariable("email") String email) {

        todoService.saveTodo(todoDTOS, email);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @PutMapping(value = "update/{email}")
    public ResponseEntity<HttpStatus> updateTodo(@PathVariable String email,
                           @RequestBody UpdateTodoDTO updateTodoDTO){

        todoService.updateTodo(email, updateTodoDTO);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }


    /**
     * 투두 불러오기
     * @param email
     * @param date
     * @return
     */
    @GetMapping(value = "select")
    public ResponseEntity<List<TodoDTO>> selectTodos(
            @RequestParam String email,
            @RequestParam String date){

        LocalDate localDate = LocalDate.parse(date);

        List<TodoDTO> todoDTOS = todoService.selectTodo(email, localDate);
        return ResponseEntity.status(HttpStatus.OK).body(todoDTOS);
    }

    @DeleteMapping(value = "complete/{email}")
    public ResponseEntity<HttpStatus> completeTodo(@PathVariable String email,
                             @RequestBody DeleteTodoDTO deleteTodoDTO){

        todoService.completeTodo(email, deleteTodoDTO);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @PatchMapping(value = "next/{email}")
    public ResponseEntity<HttpStatus> updateDateTodo(@PathVariable("email") String email, @RequestBody UpdateDateDTO updateDateDTO){

        todoService.updateDateTodo(email, updateDateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }
}
