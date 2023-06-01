package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.User.UserDTO;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUserInfo(UserDTO userDTO) {
        String email = userDTO.getEmail();

        Optional<User> userOptional = userRepository.findById(email);

        if(!userOptional.isPresent()){
            String name = userDTO.getName();
            String imgUrl = userDTO.getImgUrl();
            String mbti = userDTO.getMbti();
            int level = userDTO.getLevel();
            float exp = userDTO.getExp();

            String userPath = createFolder(email);

            User user = new User(email, name, imgUrl, mbti, userPath, level, exp);

            userRepository.save(user);
        }
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
        //노트북 경로
        /*final String userPath =
                "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/" + email;*/

        final String userPath =
                "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/chageunchageun/chageunchageun/src/main/resources/User/" + email;
        File Folder = new File(userPath);
        File MemoirFolder = new File(userPath + "/Memoir");

        if (!Folder.exists()) {
            try{
                Folder.mkdir();
                MemoirFolder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
        return userPath;
    }

    public UserDTO selectUser(String email){
        User user = userRepository.getReferenceById(email);

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setMbti(user.getMbti());
        userDTO.setImgUrl(user.getImgUrl());
        userDTO.setLevel(user.getLevel());
        userDTO.setExp(user.getExp());

        return userDTO;
    }

    /**
     * 이메일을 받아서
     * level과 exp를 업데이트
     * @param email
     */
    public void levelUp(String email, String levelExp) throws ParseException {//String level, String exp){
        User user = userRepository.getReferenceById(email);
        JSONParser parser = new JSONParser();

        JSONObject object = (JSONObject) parser.parse(levelExp);

        String level = (String) object.get("level");
        String exp = (String) object.get("exp");

        user.setLevel(Integer.parseInt(level));
        user.setExp(Float.parseFloat((exp)));

        userRepository.save(user);
    }
}
