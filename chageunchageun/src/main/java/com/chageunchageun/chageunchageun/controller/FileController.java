package com.chageunchageun.chageunchageun.controller;


import com.chageunchageun.chageunchageun.service.FileService;
import com.chageunchageun.chageunchageun.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("uploadFile")
    public ResponseEntity<String> form(@RequestParam String email,
                       @RequestParam MultipartFile file) throws IOException {

        fileService.uploadFile(email, file);


        return ResponseEntity.status(HttpStatus.OK).body("OK@@@@");
    }



}
