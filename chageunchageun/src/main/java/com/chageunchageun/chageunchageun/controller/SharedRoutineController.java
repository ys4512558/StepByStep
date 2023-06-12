package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.Routine.SharedRoutineDTO;
import com.chageunchageun.chageunchageun.service.SharedRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/shareRoutine/")
public class SharedRoutineController {

    @Autowired
    SharedRoutineService sharedRoutineService;

    /**
     * 삭제 예정
     * @param routine
     * @return
     */
    /*
    @PostMapping(value = "share")
    public ResponseEntity<HttpStatus> shareRoutine(@RequestBody String routine){

        sharedRoutineService.saveSharedRoutine(routine);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }*/

    /**
     * 클라이언트로부터 카테고리를 받아 전송하는 컨트롤러
     * 삭제 예정
     */
    /*
    @GetMapping(value = "selectCategory")
    public ResponseEntity<List<SharedRoutineDTO>> selectCategory(@RequestParam String category){
        List<SharedRoutineDTO> sharedRoutineDTOList = sharedRoutineService.selectCategory(category);

        return ResponseEntity.status(HttpStatus.OK).body(sharedRoutineDTOList);
    }*/

    /**
     * 루틴 공유 시 공유 카운트 ++
     */
    @PatchMapping(value = "shareCountPlus/{idx}")
    public ResponseEntity<HttpStatus> plusLike(@PathVariable int idx){

        sharedRoutineService.plusLikeRoutine(idx);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @PatchMapping(value = "shareCountMinus/{idx}")
    public ResponseEntity<HttpStatus> minusLike(@PathVariable int idx){

        sharedRoutineService.minusLikeRoutine(idx);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }


    @GetMapping(value = "selectMbti")
    public ResponseEntity<List<SharedRoutineDTO>> selectMbti(@RequestParam String mbti, @RequestParam String email){
        List<SharedRoutineDTO> sharedRoutineDTOS = sharedRoutineService.selectSharedRoutine(mbti, email);

        return ResponseEntity.status(HttpStatus.OK).body(sharedRoutineDTOS);
    }
    
}
