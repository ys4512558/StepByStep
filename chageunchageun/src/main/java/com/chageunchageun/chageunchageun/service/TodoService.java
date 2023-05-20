package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.TodoDTO;
import com.chageunchageun.chageunchageun.data.dto.TodosDTO;
import com.chageunchageun.chageunchageun.data.entity.Todo;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.TodoRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final UserRepository userRepository;

    private final TodoRepository todoRepository;

    public TodoService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    /**
     * 클라이언트로부터 이미지를 받아 저장하는 메서드
     * email을 primary 키로하여
     */
    public void uploadFile (String email, MultipartFile file){

        final String uploadDir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + email + "/";

        checkDir(uploadDir);

        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            System.out.println("file.getOriginalFilename = " + filename);
            String fullPath = uploadDir + filename;
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //디렉토리 생성 메서드
    public void checkDir(String path){
        File Folder = new File(path);

        if (!Folder.exists()) {
            try{
                Folder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
    }

    /**
     *
     */
    /**
     * 문자열 Todos를 @RequestBody에서 받음
     * Json으로 파싱 후 DB에 저장
     * @param todos
     */
    public void saveTodo(String todos){
        System.out.println(todos);
        JSONParser parser = new JSONParser();

        String email = null;
        String day = null;
        JSONArray todoArray = null;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(todos);
            email = (String) jsonObject.get("email");
            todoArray = (JSONArray) jsonObject.get("Todo");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = userRepository.getReferenceById(email);

        user.setTodos(parseJsonArray(todoArray));

        for(Todo todo : user.getTodos()){
            todo.setUser(user);
            todoRepository.save(todo);
        }

    }

    /**
     * JSONArray를 엔티티 List로 변환(파싱) 후 반환
     *
     */

    public List<Todo> parseJsonArray(JSONArray todoArray){

        //JSONArray에 들어갈 내용들
        String todoName;
        String todoDisc;
        LocalDate startDate;
        LocalDate endDate;
        Boolean repeat;

        List<Todo> list = new ArrayList<Todo>();
        for(Object object : todoArray){
            JSONObject todoObject = (JSONObject) object;

            todoName = (String) todoObject.get("todoName");
            todoDisc = (String) todoObject.get("todoDisc");
            startDate =  LocalDate.parse((String) todoObject.get("startDate"));
            endDate = LocalDate.parse((String) todoObject.get("endDate"));
            repeat = Boolean.parseBoolean((String)todoObject.get("oneOff"));

            Todo todo = new Todo();
            todo.setTodoName(todoName);
            todo.setTodoDisc(todoDisc);
            todo.setStartDate(startDate);
            todo.setEndDate(endDate);
            todo.setOneOff(repeat);

            list.add(todo);
        }
        return list;
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
    public TodosDTO selectTodo(String emailParam, LocalDate dateParam){

        List<Todo> todos = todoRepository.findByUserEmailAndStartDateLessThanEqualAndEndDateGreaterThanEqual(emailParam, dateParam, dateParam);

        List<TodoDTO> todoDTOS = new ArrayList<TodoDTO>();

        for (Todo todo : todos) {

            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTodoName(todo.getTodoName());
            todoDTO.setTodoDisc(todo.getTodoDisc());
            todoDTO.setStartDate(todo.getStartDate());
            todoDTO.setEndDate(todo.getEndDate());
            todoDTO.setOneOff(todo.getOneOff());

            todoDTOS.add(todoDTO);
        }

        TodosDTO todosDTO = new TodosDTO();
        todosDTO.setTodos(todoDTOS);
        todosDTO.setEmail(emailParam);

        return todosDTO;
    }
}
