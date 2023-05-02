package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.service.MemoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class MemoirController {

    @Autowired
    MemoirService memoirService;

    @PostMapping(value = "Memoir")
    public String saveMemoir(){
        
        return null;
    }


}
