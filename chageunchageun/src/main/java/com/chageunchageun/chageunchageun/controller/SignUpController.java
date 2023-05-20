package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/SignUp")
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @PostMapping()
    public ResponseEntity<HttpStatus> SignUp(@RequestBody HashMap<String, Object> userInfo){

        signUpService.saveUserInfo(userInfo);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }


}
