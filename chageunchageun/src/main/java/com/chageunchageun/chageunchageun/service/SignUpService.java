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

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String imgUrl = (String) userInfo.get("profileImgUrl");
        String mbti = (String) userInfo.get("mbti");

        String userPath = createFolder(email);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setImgUrl(imgUrl);
        user.setMbti(mbti);
        user.setUserPath(userPath);

        userRepository.save(user);
    }

    /**
     * 회원가입 시 해당 유저의 이메일을 통해 폴더를 생성
     * email 폴더
     * Routine, Todo, Memoir 하위 폴더 생성
     * @param email
     * @return
     */
    public String createFolder(String email){
        //유저 폴더 기본 경로
        //내 컴퓨터에 저장 경로
        //final String userPath = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + email;
        //프로젝트 내부 리소스 경로
        final String userPath =
                "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/" + email;

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
        return userPath;
    }
}
