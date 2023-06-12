package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.Memoir.MemoirDTO;
import com.chageunchageun.chageunchageun.data.dto.Memoir.MemoirPreviewDTO;
import com.chageunchageun.chageunchageun.data.dto.Memoir.MemoirSaveDTO;
import com.chageunchageun.chageunchageun.data.dto.User.UserMemoirDTO;
import com.chageunchageun.chageunchageun.data.entity.Memoir;
import com.chageunchageun.chageunchageun.data.entity.MemoirImg;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.MemoirImgRepository;
import com.chageunchageun.chageunchageun.data.repository.MemoirRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

        /**
         * DB에 memoir이 이미
         * 있다 => memoir은 생성X + memoirImg 추가
         * 없다 => memoir 생성 + memoirImg추가
         */
        Memoir memoir;;
        User user = userRepository.getReferenceById(email);

        Optional<Memoir> memoirOptional = memoirRepository.findTopByUserEmailAndMemoirDate(user.getEmail(), date);

        if(memoirOptional.isPresent()){
            //DB에 값이 있을 때
            memoir = memoirOptional.get();
        }
        else {
            memoir = new Memoir(user, date);
            memoirRepository.save(memoir);
        }

        MemoirImg memoirImg = new MemoirImg(memoir, imgUrl);
        memoirImgRepository.save(memoirImg);

    }

    //회고록에 넣은 이미지를 저장하는 메서드
    public String saveMemoirImg (String email, MultipartFile file, LocalDate date){

        //final String saveDir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + email + "/Memoir/" + date + "/";
        //노트북 경로
        /*final String saveDir =
                "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/" + email + "/Memoir/" + date; //+ "/";
        */

        final String saveDir =
                "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/chageunchageun/chageunchageun/src/main/resources/User/" + email + "/Memoir/" + date;

        checkDir(saveDir);
        String fullPath = "";
        String filename = "";
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();
            //System.out.println("file.getOriginalFilename = " + filename);
            fullPath = saveDir + "/" +filename;
            try {
                File temp = new File(fullPath);
                if(!temp.exists()){
                    file.transferTo(new File(fullPath));
                }
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
     * 회고록 컨텐츠 작성
     */

    public void saveContent(String memoirContent,
                            MultipartFile image){

        JSONParser parser = new JSONParser();

        String email = null;
        LocalDate date = null;
        String title = null;
        String mood = null;
        String comment = null;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(memoirContent);
            email = (String) jsonObject.get("email");
            date = LocalDate.parse((String) jsonObject.get("date"));
            title = (String) jsonObject.get("title");
            mood = (String) jsonObject.get("mood");
            comment = (String) jsonObject.get("comment");
            System.out.println(email);
            System.out.println(date);
            System.out.println(title);
            System.out.println(mood);
            System.out.println(comment);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        User user = userRepository.getReferenceById(email);

        Memoir memoir = new Memoir();
        Optional<Memoir> memoirOptional = memoirRepository.findTopByUserEmailAndMemoirDate(user.getEmail(), date);

        if(memoirOptional.isPresent()){
            //DB에 값이 있을 때
            memoir = memoirOptional.get();
        }
        else {
            memoir = new Memoir(user, date);
            memoirRepository.save(memoir);
        }

        memoir.setTitle(title);
        memoir.setComment(comment);
        memoir.setMood(mood);
        memoir.setComment(comment);

        String fileName = saveMemoirImg(email, image, date);
        MemoirImg memoirImg = new MemoirImg(memoir, fileName);

        memoirRepository.save(memoir);
        memoirImgRepository.save(memoirImg);
    }

    /*
    public void saveContent(MemoirContentDTO memoirContent,
                            MultipartFile image){

        String email = memoirContent.getEmail();
        LocalDate date = memoirContent.getDate();
        String title = memoirContent.getTitle();
        String mood = memoirContent.getMood();
        String comment = memoirContent.getComment();

        Memoir memoir;
        Optional<Memoir> memoirOptional = memoirRepository.findByUserEmailAndMemoirDate(email, date);

        if(memoirOptional.isPresent()){
            //DB에 값이 있을 때
            memoir = memoirOptional.get();
        }
        else {
            User user = userRepository.getReferenceById(email);
            memoir = new Memoir(user, date);
            memoirRepository.save(memoir);
        }

        memoir.setTitle(title);
        memoir.setComment(comment);
        memoir.setMood(mood);
        memoir.setComment(comment);

        String fileName = saveMemoirImg(email, image, date);

        MemoirImg memoirImg = new MemoirImg(memoir, fileName);

        memoirRepository.save(memoir);
        memoirImgRepository.save(memoirImg);
    }

     */


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

        Memoir memoir;
        Optional<Memoir> memoirOptional = memoirRepository.findTopByUserEmailAndMemoirDate(email, date);

        MemoirDTO memoirDTO = new MemoirDTO();

        if(memoirOptional.isPresent()){
            memoir = memoirOptional.get();

            List<MemoirImg> memoirImgs = memoirImgRepository.findByMemoirIdx(memoir.getIdx());

            List<String> imgUrls = new ArrayList<>();

            for(MemoirImg m : memoirImgs){
                String imgUrl = m.getImgUrl();
                imgUrls.add(imgUrl);
            }

            memoirDTO.setEmail(memoir.getUser().getEmail());
            memoirDTO.setDate(memoir.getMemoirDate());
            memoirDTO.setTitle(memoir.getTitle());
            memoirDTO.setMood(memoir.getMood());
            memoirDTO.setComment(memoir.getComment());
            memoirDTO.setImgUrl(imgUrls);
        }

        return memoirDTO;
    }

    /**
     * 마이페이지 들어가면 보이는 회고록 프리뷰
     */

    public UserMemoirDTO previewMemoir(String email){

        User user = userRepository.getReferenceById(email);

        UserMemoirDTO userMemoirDTO = new UserMemoirDTO();

        userMemoirDTO.setEmail(user.getEmail());
        userMemoirDTO.setName(user.getName());
        userMemoirDTO.setMbti(user.getMbti());
        userMemoirDTO.setImgUrl(user.getImgUrl());
        userMemoirDTO.setLevel(String.valueOf(user.getLevel()));
        userMemoirDTO.setExp(String.valueOf(user.getExp()));

        List<Memoir> memoirs = memoirRepository.findByUserEmail(email);

        List<MemoirPreviewDTO> memoirPreviewDTOList = new ArrayList<>();
        for(Memoir memoir : memoirs){
            MemoirPreviewDTO memoirPreviewDTO = new MemoirPreviewDTO();
            memoirPreviewDTO.setMemoirDate(memoir.getMemoirDate());
            Optional<MemoirImg> memoirImg = memoirImgRepository.findTopByImgUrlContains(String.valueOf(memoir.getMemoirDate()));

            if(memoirImg.isPresent()){
                memoirPreviewDTO.setPreviewImg(memoirImg.get().getImgUrl());
            }
            memoirPreviewDTOList.add(memoirPreviewDTO);
        }

        userMemoirDTO.setPreviewList(memoirPreviewDTOList);

        return userMemoirDTO;
    }

    /**
     * 회고록 안에 이미지를 보여주기 위해 파일을 불러오는 메서드
     * email, Date, img를 통해 DB에서 유저 경로를 가져와서
     * Date와 이미지 파일 명을 통해 해당 파일을 가져온 후 반환
     */
    public File imgView(String email, LocalDate memoirDate, String img) throws IOException {

        User user = userRepository.getReferenceById(email);

        String filePath = user.getUserPath() + "/Memoir/" +  memoirDate + "/" + img;

        File file = new File(filePath);

        return file;
    }
}
