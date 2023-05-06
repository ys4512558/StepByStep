package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.MemoirDTO;
import com.chageunchageun.chageunchageun.data.dto.MemoirSaveDTO;
import com.chageunchageun.chageunchageun.service.MemoirService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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


        MemoirSaveDTO memoirSaveDTO = new MemoirSaveDTO();

        memoirSaveDTO.setEmail(email);
        memoirSaveDTO.setDate(LocalDate.now());
        memoirSaveDTO.setComment(comment);
        memoirSaveDTO.setImg(image);

        memoirService.saveMemoir(memoirSaveDTO);

        return ResponseEntity.status(HttpStatus.OK).body("OK!!!");
    }

    /**
     *회고록 조회 컨트롤러
     */

    @GetMapping(value = "memoir")
    public ResponseEntity<MemoirDTO> selectMemoir(@RequestParam String email,
                                               @RequestParam LocalDate date){

        MemoirDTO memoirDTO = memoirService.selectMemoir(email, date);

        return ResponseEntity.status(HttpStatus.OK).body(memoirDTO);

    }

    @GetMapping("img/{email}/{memoirDate}/{img}")
    public void get(HttpServletResponse response,
                    @PathVariable("email") String email,
                    @PathVariable("memoirDate") LocalDate memoirDate,
                    @PathVariable("img") String img) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();

        File file = new File("C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/"
                + email + "/Memoir/" + memoirDate + "/" + img + ".jpg");
        if (!file.exists()) {
            //해당 파일이 존재하지 않을 때 처리
        }
        FileInputStream inputStream = new FileInputStream(file);

        int length;
        byte[] buffer = new byte[1024];

        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }


}
