package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.MemoirDTO;
import com.chageunchageun.chageunchageun.data.dto.MemoirSaveDTO;
import com.chageunchageun.chageunchageun.data.entity.Memoir;
import com.chageunchageun.chageunchageun.data.repository.MemoirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;



@Service
public class MemoirService {

    private final MemoirRepository memoirRepository;
    @Autowired
    public MemoirService(MemoirRepository memoirRepository) {
        this.memoirRepository = memoirRepository;
    }

    /**
     * 회고록 저장 로직
     * 1. 클라이언트로부터 email, comment, img를 받음
     * 2. 컨트롤러에서 DTO로 변환
     * 3. DTO를 통해 Memoir 엔티티 객체 생성
     * 4. DB에 저장
     * idx : auto increse (primary key),
     * email : 사용자 이메일
     * comment : 회고 코멘트
     * date : 작성일
     * img_url : 이미지 저장 경로
     */

    public void saveMemoir(MemoirSaveDTO memoirSaveDTO){

        String email = memoirSaveDTO.getEmail();
        LocalDate date = memoirSaveDTO.getDate();
        String routineName = memoirSaveDTO.getRoutineName();
        String imgUrl = "";
        if(memoirSaveDTO.getImg() != null){
            imgUrl = saveMemoirImg(email, memoirSaveDTO.getImg(),date);
        }
        System.out.println("email" + email);
        System.out.println("date" + date);
        System.out.println("routineName" + routineName);
        System.out.println("imgUrl" + imgUrl);

        Memoir memoir = new Memoir();
        memoir.setEmail(email);
        memoir.setMemoirDate(date);
        memoir.setImgUrl(imgUrl);

        memoirRepository.save(memoir);
    }

    //회고록에 넣은 이미지를 저장하는 메서드
    public String saveMemoirImg (String email, MultipartFile file, LocalDate date){

        //final String saveDir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + email + "/Memoir/" + date + "/";
        final String saveDir =
                "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/" + email + "/Memoir/" + date; //+ "/";

        checkDir(saveDir);
        String fullPath = "";
        String filename = "";
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            //System.out.println("file.getOriginalFilename = " + filename);
            fullPath = saveDir + "/" +filename;
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return filename;
        //return fullPath;
    }

    //폴더 생성
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

    /**
     * 회고록 불러오는 로직
     * 1. 클라이언트로부터 email, date를 받음
     * 2. email, date를 통해 DB회고록 Select
     * 3. comment, img_url가져오기
     * 4. comment + 이미지 이름 전송
     * 5. 클라이언트에서 다시 img_url을 통해 이미지 요청
     * 6. 이미지를 주는 api에서 이를 처리
     */

    public MemoirDTO selectMemoir(String email, LocalDate date){

        Memoir memoir = memoirRepository.findByEmailAndMemoirDate(email, date);

        MemoirDTO memoirDTO = new MemoirDTO();
        memoirDTO.setEmail(memoir.getEmail());
        memoirDTO.setDate(memoir.getMemoirDate());
        memoirDTO.setComment(memoir.getComment());
        memoirDTO.setImgUrl(memoir.getImgUrl());

        return memoirDTO;
    }

}
