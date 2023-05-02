package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.MemoirDTO;
import com.chageunchageun.chageunchageun.data.entity.Memoir;
import com.chageunchageun.chageunchageun.service.MemoirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/")
public class MemoirController {
    @Autowired
    MemoirService memoirService;

    private final Logger LOGGER = LoggerFactory.getLogger(MemoirController.class);

    /**
     *회고록 저장 컨트롤러
     */
    @PostMapping(value = "memoir")
    public ResponseEntity<String> saveMemoir(@RequestParam String email,
                                       @RequestParam String comment,
                                       @RequestParam MultipartFile image) throws IOException {


        MemoirDTO memoirDTO = new MemoirDTO();

        memoirDTO.setEmail(email);
        memoirDTO.setDate(LocalDate.now());
        memoirDTO.setComment(comment);
        memoirDTO.setImg(image);

        memoirService.saveMemoir(memoirDTO);

        return ResponseEntity.status(HttpStatus.OK).body("OK!!!");
    }

    /**
     *회고록 조회 컨트롤러
     */

    @GetMapping(value = "memoir")
    public ResponseEntity<String> selectMemoir(@RequestParam String email,
                                               @RequestParam LocalDate date){

        memoirService.selectMemoir(email, date);

        return ResponseEntity.status(HttpStatus.OK).body("dd");

    }


}
