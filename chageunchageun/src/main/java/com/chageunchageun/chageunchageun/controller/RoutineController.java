package com.chageunchageun.chageunchageun.controller;


import com.chageunchageun.chageunchageun.data.dto.RoutinesDTO;
import com.chageunchageun.chageunchageun.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class RoutineController {
    @Autowired
    RoutineService routineService;

    @PostMapping(value = "Routines")
    public ResponseEntity<HttpStatus> saveRoutines(@RequestBody String routines){

        routineService.saveRoutines(routines);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }


    @GetMapping(value = "Routines")
    public ResponseEntity<RoutinesDTO> selectRoutines(
            @RequestParam String email,
            @RequestParam String day){

        RoutinesDTO routinesDTO = routineService.selectRoutines(email, day);

        System.out.println(routinesDTO);

        return ResponseEntity.status(HttpStatus.OK).body(routinesDTO);
    }


}
