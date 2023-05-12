package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import com.chageunchageun.chageunchageun.data.repository.SharedRoutineRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        //JsonArray
        JSONArray routines;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(sharedRoutine);
            mbti = (String) jsonObject.get("mbti");
            nickName = (String) jsonObject.get("nickName");
            sharedDate = (LocalDate) jsonObject.get("sharedDate");
            routines = (JSONArray) jsonObject.get("sharedRoutines");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<SharedRoutine> list = parseJsonArray(routines, mbti, nickName, sharedDate);

        //리턴받은 List를 DB에 저장하는 부분 구현 필요

    }
    public List<SharedRoutine> parseJsonArray(JSONArray routines,
                                              String mbti, String nickName, LocalDate sharedDate){

        //JSONArray에 들어갈 내용들
        String categori;
        String routineName;
        String routineContent;
        String routineExplain;
        String start;
        String end;
        int count;
        
        List<SharedRoutine> list = new ArrayList<SharedRoutine>();
        for(Object object : routines){
            JSONObject routineObject = (JSONObject) object;

            categori = (String) routineObject.get("categori");
            routineName = (String) routineObject.get("routineName");
            routineContent = (String) routineObject.get("routineContent");
            routineExplain = (String) routineObject.get("routineExplain");
            start = (String) routineObject.get("start");
            end = (String) routineObject.get("end");
            count = (int) routineObject.get("count");
            
            SharedRoutine sharedRoutine = new SharedRoutine();
            sharedRoutine.setCategori(categori);
            sharedRoutine.setRoutineName(routineName);
            sharedRoutine.setRoutineContent(routineContent);
            sharedRoutine.setRoutineExplain(routineExplain);
            sharedRoutine.setStart(start);
            sharedRoutine.setEnd(end);
            //처음 루틴 공유 시 0으로 초기화
            sharedRoutine.setCount(0);
            
            sharedRoutine.setMbti(mbti);
            sharedRoutine.setNickName(nickName);
            sharedRoutine.setSharedDate(sharedDate);
            
            list.add(sharedRoutine);
        }
        return list;
    }

}
