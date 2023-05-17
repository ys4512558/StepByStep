package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.MemoirDTO;
import com.chageunchageun.chageunchageun.data.dto.MemoirSaveDTO;
import com.chageunchageun.chageunchageun.data.entity.Memoir;
import com.chageunchageun.chageunchageun.data.entity.MemoirImg;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.MemoirImgRepository;
import com.chageunchageun.chageunchageun.data.repository.MemoirRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MemoirService {
    private final UserRepository userRepository;
    private final MemoirRepository memoirRepository;
    private final MemoirImgRepository memoirImgRepository;
    @Autowired
    public MemoirService(MemoirRepository memoirRepository, UserRepository userRepository, MemoirImgRepository memoirImgRepository) {
        this.memoirRepository = memoirRepository;
        this.userRepository = userRepository;
        this.memoirImgRepository = memoirImgRepository;
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
        String itemName = memoirSaveDTO.getItemName();
        String imgUrl = "";
        if(memoirSaveDTO.getImg() != null){
            imgUrl = saveMemoirImg(email, memoirSaveDTO.getImg(),date);
        }
        System.out.println("email" + email);
        System.out.println("date" + date);
        System.out.println("itemName" + itemName);
        System.out.println("imgUrl" + imgUrl);

        /**
         * DB에 memoir이 이미
         * 있다 => memoir은 생성X + memoirImg 추가
         * 없다 => memoir 생성 + memoirImg추가
         */
        Memoir memoir = new Memoir();
        User user = userRepository.getReferenceById(email);

        Optional<Memoir> memoirOptional = memoirRepository.findByUserEmail(user.getEmail());

        if(memoirOptional.isPresent()){
            //DB에 값이 있을 때
            memoir = memoirOptional.get();
        }
        else {
            memoir.setUser(user);
            memoir.setMemoirDate(date);
            memoirRepository.save(memoir);
        }


        MemoirImg memoirImg = new MemoirImg();
        memoirImg.setMemoir(memoir);
        memoirImg.setImgUrl(imgUrl);
        memoirImgRepository.save(memoirImg);

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

        Memoir memoir = memoirRepository.findByUserEmailAndMemoirDate(email, date);

        List<MemoirImg> memoirImgs = memoirImgRepository.findByMemoirIdx(memoir.getIdx());

        List<String> imgUrls = new ArrayList<>();

        for(MemoirImg m : memoirImgs){
            String imgUrl = m.getImgUrl();
            imgUrls.add(imgUrl);
        }


        MemoirDTO memoirDTO = new MemoirDTO();
        memoirDTO.setEmail(memoir.getUser().getEmail());
        memoirDTO.setDate(memoir.getMemoirDate());
        memoirDTO.setComment(memoir.getComment());
        memoirDTO.setImgUrl(imgUrls);

        return memoirDTO;
    }

}
