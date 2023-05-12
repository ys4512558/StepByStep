package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.SharedRoutineDTO;
import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import com.chageunchageun.chageunchageun.data.repository.SharedRoutineRepository;
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


    private final SharedRoutineRepository sharedRoutineRepository;
    @Autowired
    public SharedRoutineService(SharedRoutineRepository sharedRoutineRepository) {
        this.sharedRoutineRepository = sharedRoutineRepository;
    }

    /**
     * 1. 공유된 루틴 DB에 저장
     * 2. 공유된 루틴을 커뮤니티에 불러올 수 있도록 전송
     */

    public void saveSharedRoutine(String sharedRoutine){
        JSONParser parser = new JSONParser();

        //mbti, nickName, sharedDate는 하나만 있어도 됨
        String mbti;
        String nickName;
        LocalDate sharedDate;
        //공유할 루틴에 각각 다른 정보가 저장될 수 있음
        //따라서 JsonArray 사용
        JSONArray routines;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(sharedRoutine);
            mbti = (String) jsonObject.get("mbti");
            nickName = (String) jsonObject.get("nickName");
            sharedDate = LocalDate.parse((String) jsonObject.get("sharedDate"), DateTimeFormatter.ISO_DATE);
            routines = (JSONArray) jsonObject.get("sharedRoutines");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<SharedRoutine> list = parseJsonArray(routines, mbti, nickName, sharedDate);

        //DB에 저장
        for (SharedRoutine routine : list){
            sharedRoutineRepository.save(routine);
        }
    }

    /**
     * JsonArray를 파싱한 후 리스트로 리턴
     * @param routines
     * @param mbti
     * @param nickName
     * @param sharedDate
     * @return
     */
    public List<SharedRoutine> parseJsonArray(JSONArray routines,
                                              String mbti, String nickName, LocalDate sharedDate){

        //JSONArray에 들어갈 내용들
        String category;
        String routineName;
        String routineContent;
        String routineExplain;
        String start;
        String end;
        int count;
        
        List<SharedRoutine> list = new ArrayList<SharedRoutine>();
        for(Object object : routines){
            JSONObject routineObject = (JSONObject) object;

            category = (String) routineObject.get("category");
            routineName = (String) routineObject.get("routineName");
            routineContent = (String) routineObject.get("routineContent");
            routineExplain = (String) routineObject.get("routineExplain");
            start = (String) routineObject.get("start");
            end = (String) routineObject.get("end");
            count =  Integer.parseInt((String) routineObject.get("count"));
            
            SharedRoutine sharedRoutine = new SharedRoutine();
            sharedRoutine.setCategory(category);
            sharedRoutine.setRoutineName(routineName);
            sharedRoutine.setRoutineContent(routineContent);
            sharedRoutine.setRoutineExplain(routineExplain);
            sharedRoutine.setStart(start);
            sharedRoutine.setEnd(end);
            sharedRoutine.setCount(count);
            
            sharedRoutine.setMbti(mbti);
            sharedRoutine.setNickName(nickName);
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
            SharedRoutineDTO sharedRoutineDTO = new SharedRoutineDTO();

            //DTO초기화
            sharedRoutineDTO.setMbti(sharedRoutine.getMbti());
            sharedRoutineDTO.setNickName(sharedRoutine.getNickName());
            sharedRoutineDTO.setCategory(sharedRoutine.getCategory());
            sharedRoutineDTO.setRoutineName(sharedRoutine.getRoutineName());
            sharedRoutineDTO.setRoutineContent(sharedRoutine.getRoutineContent());
            sharedRoutineDTO.setRoutineExplain(sharedRoutine.getRoutineExplain());
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
