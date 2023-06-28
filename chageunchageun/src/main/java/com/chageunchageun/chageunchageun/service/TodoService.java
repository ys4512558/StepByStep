package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.Todo.DeleteTodoDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.TodoDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.UpdateTodoDTO;
import com.chageunchageun.chageunchageun.data.dto.Todo.UpdateDateDTO;
import com.chageunchageun.chageunchageun.data.entity.Todo;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.TodoRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final UserRepository userRepository;

    private final TodoRepository todoRepository;

    public TodoService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    /**
     * todosDTO를 통해 저장할 투두를 받아서 저장
     * @param todosDTO
     * @param email
     */
    public void saveTodo(List<TodoDTO> todosDTO, String email){

        User user = userRepository.getReferenceById(email);

        List<Todo> todos = new ArrayList<>();

        for(TodoDTO todoDTO : todosDTO) {
            Todo todo = new Todo(user,
                    todoDTO.getTodo_name(),
                    todoDTO.getTodo_disc(),
                    todoDTO.getStart_date(),
                    todoDTO.getEnd_date());

            todos.add(todo);
            todoRepository.save(todo);
        }
    }


    /**
     * 투두를 수정하기 위한 메소드
     * @param email
     * @param updateTodoDTO
     */
    public void updateTodo(String email, UpdateTodoDTO updateTodoDTO){

        User user = userRepository.getReferenceById(email);

        String todo_name = updateTodoDTO.getTodo_name();
        String todo_disc = updateTodoDTO.getTodo_disc();

        Optional<Todo> todoOptional = todoRepository.findTopByUserEmailAndTodoNameAndTodoDisc(user.getEmail(),
                todo_name, todo_disc);

        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();

            todo.setTodoName(updateTodoDTO.getTodo_nameRp());
            todo.setTodoDisc(updateTodoDTO.getTodo_discRp());
            todo.setStartDate(updateTodoDTO.getStart_date());
            todo.setEndDate(updateTodoDTO.getEnd_date());

            todoRepository.save(todo);
        }
    }

    /**
     * DB에서 이메일, 요일로 검색
     * 해당 데이터 RoutineDTO에 저장
     * ArrayList<RoutineDTO>에 저장
     * RoutinesDTO에 이메일, 요일, List<RoutineDTO>저장
     * RoutinesDTO 리턴
     * @param emailParam
     * @param dateParam
     * @return
     */
    public List<TodoDTO> selectTodo(String emailParam, LocalDate dateParam){

        List<Todo> todos = todoRepository.findByUserEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqual(emailParam, dateParam, dateParam);

        List<TodoDTO> todoDTOS = new ArrayList<TodoDTO>();

        for (Todo todo : todos) {

            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTodo_name(todo.getTodoName());
            todoDTO.setTodo_disc(todo.getTodoDisc());
            todoDTO.setStart_date(todo.getStartDate());
            todoDTO.setEnd_date(todo.getEndDate());

            todoDTOS.add(todoDTO);
        }

        return todoDTOS;
    }

    /**
     * 투두 완료 시 삭제하기 위한 메소드
     * @param email
     * @param deleteTodoDTO
     */
    public void completeTodo(String email, DeleteTodoDTO deleteTodoDTO){

        User user = userRepository.getReferenceById(email);

        String todo_name = deleteTodoDTO.getTodo_name();
        String todo_disc = deleteTodoDTO.getTodo_disc();

        Optional<Todo> todoOptional = todoRepository.findTopByUserEmailAndTodoNameAndTodoDisc(user.getEmail(), todo_name, todo_disc);

        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();

            todoRepository.delete(todo);
        }
    }

    /**
     * 날짜 미루기를 위한 메소드
     * 마감 기한이 하루 증가한다.
     * @param email
     * @param updateDateDTO
     */
    public void updateDateTodo(String email, UpdateDateDTO updateDateDTO){
        User user = userRepository.getReferenceById(email);

        String todo_name = updateDateDTO.getTodo_name();
        String todo_disc = updateDateDTO.getTodo_disc();

        Optional<Todo> todoOptional = todoRepository.findTopByUserEmailAndTodoNameAndTodoDisc(user.getEmail(), todo_name, todo_disc);

        if(todoOptional.isPresent()){
            Todo todo = todoOptional.get();
            todo.setEndDate(updateDateDTO.getEnd_dateRp());

            todoRepository.save(todo);
        }
    }
}
