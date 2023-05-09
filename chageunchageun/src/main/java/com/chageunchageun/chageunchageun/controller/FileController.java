package com.chageunchageun.chageunchageun.controller;


import com.chageunchageun.chageunchageun.data.dto.RoutineDTO;
import com.chageunchageun.chageunchageun.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    /*
    @PostMapping(value = "json")
    public ResponseEntity<List<HashMap<String ,Object>>> TransportJson(
            @RequestBody String jsonArray){

        //System.out.println(jsonArray);
        List<HashMap<String,Object>> list = fileService.jsonFile(jsonArray);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }*/

    @PostMapping(value = "json")
    public ResponseEntity<List<RoutineDTO>> TransportJson2(
            @RequestBody String jsonArray,
            @RequestParam("email") String email){

        System.out.println(email);

        fileService.jsonFile2(jsonArray);
        List<RoutineDTO> list = fileService.ReadJsonFile();

        System.out.println(list);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @GetMapping(value = "json")
    public ResponseEntity<List<RoutineDTO>> TransportJson3(){

        List<RoutineDTO> list = fileService.ReadJsonFile();

        System.out.println(list);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


}
