package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.service.SharedRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/")
public class SharedRoutinController {

    @Autowired
    SharedRoutineService sharedRoutineService;

    //루틴공유 한번에 하나만 가능할 때
    @PostMapping(value = "ShareRoutine")
    public void shareRoutine(@RequestBody String routine){

    }
}
