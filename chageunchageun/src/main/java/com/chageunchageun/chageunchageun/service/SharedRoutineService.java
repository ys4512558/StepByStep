package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.dto.SharedRoutineDTO;
import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import com.chageunchageun.chageunchageun.data.repository.SharedRoutineRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SharedRoutineService {


    private final UserRepository userRepository;
    private final SharedRoutineRepository sharedRoutineRepository;
    @Autowired
    public SharedRoutineService(UserRepository userRepository, SharedRoutineRepository sharedRoutineRepository) {
        this.userRepository = userRepository;
        this.sharedRoutineRepository = sharedRoutineRepository;
    }

    /**
     * 1. 공유된 루틴 DB에 저장
     * 2. 공유된 루틴을 커뮤니티에 불러올 수 있도록 전송
     */

    public void saveSharedRoutine(String sharedRoutine){
        JSONParser parser = new JSONParser();

        String email;
        LocalDate sharedDate;
        //공유할 루틴에 각각 다른 정보가 저장될 수 있음
        //따라서 JsonArray 사용
        JSONArray routines;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(sharedRoutine);
            email = (String) jsonObject.get("email");
            sharedDate = LocalDate.parse((String) jsonObject.get("sharedDate"), DateTimeFormatter.ISO_DATE);
            routines = (JSONArray) jsonObject.get("sharedRoutines");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<SharedRoutine> list = parseJsonArray(routines, email,sharedDate);

        //DB에 저장
        for (SharedRoutine routine : list){
            sharedRoutineRepository.save(routine);
        }
    }

    /**
     * JsonArray를 파싱한 후 리스트로 리턴
     * @param routines
     * @param sharedDate
     * @return
     */
    public List<SharedRoutine> parseJsonArray(JSONArray routines, String email,LocalDate sharedDate){

        //JSONArray에 들어갈 내용들
        String category;
        String itemName;
        String itemDisc;
        String itemExplain;
        String start;
        String end;
        int count;
        
        List<SharedRoutine> list = new ArrayList<SharedRoutine>();
        for(Object object : routines){
            JSONObject routineObject = (JSONObject) object;

            //유저의 정보 가져오기
            User user = userRepository.getReferenceById(email);

            category = (String) routineObject.get("category");
            itemName = (String) routineObject.get("itemName");
            itemDisc = (String) routineObject.get("itemDisc");
            itemExplain = (String) routineObject.get("itemExplain");
            start = (String) routineObject.get("start");
            end = (String) routineObject.get("end");

            //클라이언트한테 받을 때 count가 필요한가?
            count =  Integer.parseInt((String) routineObject.get("count"));
            
            SharedRoutine sharedRoutine = new SharedRoutine();
            //유저 정보(이메일)
            sharedRoutine.setUser(user);

            sharedRoutine.setCategory(category);
            sharedRoutine.setItemName(itemName);
            sharedRoutine.setItemDisc(itemDisc);
            sharedRoutine.setItemExplain(itemExplain);
            sharedRoutine.setStart(start);
            sharedRoutine.setEnd(end);
            sharedRoutine.setCount(count);
            sharedRoutine.setSharedDate(sharedDate);
            
            list.add(sharedRoutine);
        }
        return list;
    }

    /**
     * 카테고리를 통해 루틴을 전송하는 메서드
     */
    public List<SharedRoutineDTO> selectCategory(String category){

        List<SharedRoutine> sharedRoutines = sharedRoutineRepository.findByCategory(category);
        List<SharedRoutineDTO> sharedRoutineDTOS = new ArrayList<SharedRoutineDTO>();

        for(SharedRoutine sharedRoutine : sharedRoutines){

            String email = sharedRoutine.getUser().getEmail();
            String name = sharedRoutine.getUser().getName();
            String mbti = sharedRoutine.getUser().getMbti();

            SharedRoutineDTO sharedRoutineDTO = new SharedRoutineDTO();

            //DTO초기화
            sharedRoutineDTO.setIdx(sharedRoutine.getIdx());
            sharedRoutineDTO.setEmail(email);
            sharedRoutineDTO.setMbti(mbti);
            sharedRoutineDTO.setName(name);
            sharedRoutineDTO.setCategory(sharedRoutine.getCategory());
            sharedRoutineDTO.setItemName(sharedRoutine.getItemName());
            sharedRoutineDTO.setItemDisc(sharedRoutine.getItemDisc());
            sharedRoutineDTO.setItemExplain(sharedRoutine.getItemExplain());
            sharedRoutineDTO.setStart(sharedRoutine.getStart());
            sharedRoutineDTO.setEnd(sharedRoutine.getEnd());
            sharedRoutineDTO.setCount(sharedRoutine.getCount());
            sharedRoutineDTO.setSharedDate(sharedRoutine.getSharedDate());

            sharedRoutineDTOS.add(sharedRoutineDTO);
        }

        return sharedRoutineDTOS;

    }

    public List<SharedRoutineDTO> selectMbti(String mbtiParam){

        List<Object[]> sharedRoutines = sharedRoutineRepository.getSharedRoutinesWithUserMbti(mbtiParam);
        List<SharedRoutineDTO> sharedRoutineDTOS = new ArrayList<SharedRoutineDTO>();

        for(Object[] result : sharedRoutines){

            String mbti = (String) result[0];
            String email = (String) result[1];
            String name = (String) result[2];
            SharedRoutine sharedRoutine = (SharedRoutine) result[3];

            SharedRoutineDTO sharedRoutineDTO = new SharedRoutineDTO();

            //DTO초기화
            sharedRoutineDTO.setIdx(sharedRoutine.getIdx());
            sharedRoutineDTO.setEmail(email);
            sharedRoutineDTO.setMbti(mbti);
            sharedRoutineDTO.setName(name);
            sharedRoutineDTO.setCategory(sharedRoutine.getCategory());
            sharedRoutineDTO.setItemName(sharedRoutine.getItemName());
            sharedRoutineDTO.setItemDisc(sharedRoutine.getItemDisc());
            sharedRoutineDTO.setItemExplain(sharedRoutine.getItemExplain());
            sharedRoutineDTO.setStart(sharedRoutine.getStart());
            sharedRoutineDTO.setEnd(sharedRoutine.getEnd());
            sharedRoutineDTO.setCount(sharedRoutine.getCount());
            sharedRoutineDTO.setSharedDate(sharedRoutine.getSharedDate());

            sharedRoutineDTOS.add(sharedRoutineDTO);
        }

        return sharedRoutineDTOS;

    }
    /**
     * 루틴 공유시 count++ 메서드
     * @param idx
     */
    @Transactional
    public void updateRoutine(Integer idx) {
        SharedRoutine sharedRoutine = sharedRoutineRepository.findById(idx).orElseThrow(
                ()-> new IllegalArgumentException("수정 실패"));
        Integer cnt = sharedRoutine.getCount() + 1;
        sharedRoutine.setCount(cnt);


    }
}
