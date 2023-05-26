package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.User.UserDTO;
import com.chageunchageun.chageunchageun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "SignUp")
    public void SignUp(@RequestBody UserDTO userDTO){

        userService.saveUserInfo(userDTO);
    }

    @GetMapping(value = "userInfo")
    public ResponseEntity<UserDTO> myPage(@RequestParam String email){

        UserDTO userDTO = userService.selectUser(email);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PatchMapping(value = "userLevel/{email}")
    public void levelUp(@PathVariable String email,
                        @RequestParam int level,
                        @RequestParam int exp){

        userService.levelUp(email, level, exp);

    }



}
