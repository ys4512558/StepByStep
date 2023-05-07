package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.RutineDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {


    /**
     * 클라이언트로부터 이미지를 받아 저장하는 메서드
     * email을 primary 키로하여
     * 
     */
    public void uploadFile (String email, MultipartFile file){

        final String uploadDir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + email + "/";

        checkDir(uploadDir);

        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            System.out.println("file.getOriginalFilename = " + filename);
            String fullPath = uploadDir + filename;
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //디렉토리 생성 메서드
    public void checkDir(String path){
        File Folder = new File(path);

        if (!Folder.exists()) {
            try{
                Folder.mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
    }

    public void jsonFile (List<RutineDTO> rutines){

        final String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/";

        /**
         * Json배열을 파일로 저장하고 저장한 파일을 전송하는 메서드 구현
         */
    }
}
