package com.chageunchageun.chageunchageun.controller;


import com.chageunchageun.chageunchageun.service.FileService;
import com.chageunchageun.chageunchageun.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping(value = "SchduleFile")
    public ResponseEntity<HttpStatus> form(@RequestParam String email,
                       @RequestParam MultipartFile file) throws IOException {

        fileService.uploadFile(email, file);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @PostMapping(value = "json")
    public ResponseEntity<HttpStatus> TransJson(@RequestBody HashMap<String, Object> map){

        System.out.println(map);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }



}
