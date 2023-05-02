package com.chageunchageun.chageunchageun.controller;

import com.chageunchageun.chageunchageun.dto.KakaoDTO;
import com.chageunchageun.chageunchageun.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/")
public class KakaoController {

    @Autowired
    KakaoService kakaoService;

    @GetMapping("auth/Page")
    public String kakaoPage(){
        return "index";
    }


    @GetMapping("auth/KakaoLogin")
    public ResponseEntity<KakaoDTO> KakaoLogin(@RequestParam("code") String code){
        String access_Token = kakaoService.getAccessToken(code);
        KakaoDTO kakaoDTO = kakaoService.getUserInfo(access_Token);

        return ResponseEntity.status(HttpStatus.OK).body(kakaoDTO);
    }

}
