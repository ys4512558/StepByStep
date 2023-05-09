package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

@Service
public class SignUpService {

    private final UserRepository userRepository;

    @Autowired
    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUserInfo(HashMap<String, Object> userInfo) {

        String email = (String) userInfo.get("userID");
        String imgUrl = (String) userInfo.get("profileImgUrl");
        /**
         * mbti 받기
         * String mbti = (String) userInfo.get("mbti");
         */

        User user = new User();
        user.setEmail(email);
        user.setImgUrl(imgUrl);

        userRepository.save(user);

        createFolder(email);

    }

    public void createFolder(String email){
        //유저 폴더 기본 경로
        final String userPath = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + email;

        File Folder = new File(userPath);
        File RotineFolder = new File(userPath + "/Routine");
        File TodoFolder = new File(userPath + "/Todo");
        File MemoirFolder = new File(userPath + "/Memoir");

        if (!Folder.exists()) {
            try{
                Folder.mkdir();
                RotineFolder.mkdir();
                TodoFolder.mkdir();
                MemoirFolder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
    }
}
