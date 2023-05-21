package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.UserDTO;
import com.chageunchageun.chageunchageun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "SignUp")
    public ResponseEntity<HttpStatus> SignUp(@RequestBody HashMap<String, Object> userInfo){

        userService.saveUserInfo(userInfo);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @GetMapping(value = "userInfo")
    public ResponseEntity<UserDTO> myPage(@RequestParam String email){

        UserDTO userDTO = userService.selectUser(email);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }


}
