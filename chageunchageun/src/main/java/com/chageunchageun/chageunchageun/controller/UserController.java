package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.data.dto.User.UserDTO;
import com.chageunchageun.chageunchageun.service.UserService;
import org.json.simple.parser.ParseException;
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
    public ResponseEntity<HttpStatus> SignUp(@RequestBody UserDTO userDTO){

        userService.saveUserInfo(userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);
    }

    @GetMapping(value = "userInfo")
    public ResponseEntity<UserDTO> myPage(@RequestParam String email){

        UserDTO userDTO = userService.selectUser(email);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PatchMapping(value = "userLevel/{email}")
    public ResponseEntity<HttpStatus> levelUp(@PathVariable String email,
                        @RequestBody String levelExp) throws ParseException {


        userService.levelUp(email, levelExp);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK);

    }



}
