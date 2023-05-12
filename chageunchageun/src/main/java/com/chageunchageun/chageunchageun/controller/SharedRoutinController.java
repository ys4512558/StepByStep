package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.SharedRoutineDTO;
import com.chageunchageun.chageunchageun.service.SharedRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class SharedRoutinController {

    @Autowired
    SharedRoutineService sharedRoutineService;

    //루틴공유
    @PostMapping(value = "ShareRoutine")
    public ResponseEntity<HttpStatus> shareRoutine(@RequestBody String routine){

        sharedRoutineService.saveSharedRoutine(routine);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    /**
     * 클라이언트로부터 카테고리를 받아 전송하는 컨트롤러
     */
    @GetMapping(value = "ShareRoutine")
    public ResponseEntity<List<SharedRoutineDTO>> selectCategory(@RequestParam String category){
        List<SharedRoutineDTO> sharedRoutineDTOList = sharedRoutineService.selectCategory(category);

        return ResponseEntity.status(HttpStatus.OK).body(sharedRoutineDTOList);
    }

    /**
     * 루틴 공유 시 공유 카운트 ++
     */
    @PutMapping(value = "ShareRoutine/{idx}")
    public ResponseEntity<HttpStatus> updateCount(@PathVariable int idx){

        sharedRoutineService.updateRoutine(idx);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    
}
