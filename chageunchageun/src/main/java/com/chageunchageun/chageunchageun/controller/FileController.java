package com.chageunchageun.chageunchageun.controller;


import com.chageunchageun.chageunchageun.data.dto.RutineArrayDTO;
import com.chageunchageun.chageunchageun.data.dto.RutineDTO;
import com.chageunchageun.chageunchageun.service.FileService;
import com.chageunchageun.chageunchageun.service.KakaoService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    /*
    @PostMapping(value = "json")
    public ResponseEntity<HttpStatus> TransJson(@RequestBody HashMap<String, Object> map){

        System.out.println(map);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }*/

    @PostMapping(value = "json")
    public ResponseEntity<List<HashMap<String ,Object>>> TransportJson(
            @RequestBody String jsonArray){

        //System.out.println(jsonArray);
        List<HashMap<String,Object>> list = fileService.jsonFile(jsonArray);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


}
