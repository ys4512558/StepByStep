package com.chageunchageun.chageunchageun.controller;


import com.chageunchageun.chageunchageun.data.dto.MemoirSaveDTO;
import com.chageunchageun.chageunchageun.data.dto.RoutineDTO;
import com.chageunchageun.chageunchageun.data.dto.RoutinesDTO;
import com.chageunchageun.chageunchageun.data.dto.UpdateRoutineDTO;
import com.chageunchageun.chageunchageun.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/routine/")
public class RoutineController {
    @Autowired
    RoutineService routineService;



    /*@PostMapping(value = "save")
    public ResponseEntity<HttpStatus> saveRoutines(@RequestBody String routines){

        routineService.saveRoutine(routines);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }*/
    /**
     * 루틴 저장
     * @param routinesDTO
     * @return
     */
    @PostMapping(value = "save")
    public void saveRoutinesDTO(@RequestBody RoutinesDTO routinesDTO){

        routineService.saveRoutineDTO(routinesDTO);
    }


    /**
     * 루틴 불러오기
     * @param email
     * @param day
     * @return
     */
    @GetMapping(value = "select")
    public ResponseEntity<RoutinesDTO> selectRoutines(
            @RequestParam String email,
            @RequestParam String day){

        RoutinesDTO routinesDTO = routineService.selectRoutine(email, day);
        System.out.println(routinesDTO);

        return ResponseEntity.status(HttpStatus.OK).body(routinesDTO);
    }

    @PatchMapping(value = "update/{email}")
    public void updateRoutine(@PathVariable String email,
                              @RequestBody UpdateRoutineDTO updateRoutine){

        routineService.updateRoutine(email, updateRoutine);
    }

}
