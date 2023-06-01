package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.entity.Routine;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.dto.Routine.SharedRoutineDTO;
import com.chageunchageun.chageunchageun.data.repository.RoutineRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SharedRoutineService {


    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    @Autowired
    public SharedRoutineService(UserRepository userRepository, RoutineRepository routineRepository) {
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
    }

    /**
     * JsonArray를 파싱한 후 리스트로 리턴
     * @param routines
     * @param sharedDate
     * @return
     */
    /*
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
    }*/

    /**
     * 카테고리를 통해 루틴을 전송하는 메서드
     */

    /*
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
     */

    /**
     * 해당하는 mbti의 루틴을 모두 가져오는 메소드
     * @param mbtiParam
     * @return
     */
    public List<SharedRoutineDTO> selectMbti(String mbtiParam){

        List<Object[]> sharedRoutines = routineRepository.getSharedRoutinesWithUserMbti(mbtiParam);
        List<SharedRoutineDTO> sharedRoutineDTOS = new ArrayList<SharedRoutineDTO>();

        for(Object[] result : sharedRoutines){

            Routine routine = (Routine) result[0];

            SharedRoutineDTO sharedRoutineDTO = new SharedRoutineDTO();

            //DTO초기화
            sharedRoutineDTO.setIdx(routine.getIdx());
            sharedRoutineDTO.setItemName(routine.getItemName());
            sharedRoutineDTO.setItemDisc(routine.getItemDisc());
            sharedRoutineDTO.setStart(routine.getStart());
            sharedRoutineDTO.setEnd(routine.getEnd());
            sharedRoutineDTO.setLike_cnt(routine.getLike_cnt());

            sharedRoutineDTOS.add(sharedRoutineDTO);
        }

        return sharedRoutineDTOS;

    }

    /**
     * MBTI입력받아서
     * 10개 이상 있으면 10개만 랜덤으로 루틴 리턴
     * @param mbtiParam
     * @return
     */
    public List<SharedRoutineDTO> selectSharedRoutine(String mbtiParam){
        List<Integer> sharedIdx = routineRepository.getIdxWithUserMbti(mbtiParam);

        List<SharedRoutineDTO> sharedRoutineDTOS = new ArrayList<SharedRoutineDTO>();

        Random random = new Random();
        List<Integer> randIdx = new ArrayList<>();

        int num = 0;
        if(sharedIdx.size() >= 10){
            num = 10;
        }
        else{
            num = sharedIdx.size();
        }

        boolean check = false;

        for(int i = 0; i < num; i++){
            int randNum = random.nextInt(num);
            check = false;
            for(int j = 0; j < randIdx.size(); j++){
                if(randNum == randIdx.get(j)){
                    check = true;
                }
            }
            if(!check){
                randIdx.add(randNum);
            }
            else{
                i--;
            }
        }

        List<Routine> routines = new ArrayList<>();
        for(int i = 0; i < num; i++){
            int idx = sharedIdx.get(randIdx.get(i));
            Routine routine = routineRepository.getReferenceById(idx);
            routines.add(routine);
        }

        for(Routine routine : routines){
            SharedRoutineDTO sharedRoutineDTO = new SharedRoutineDTO();

            //DTO초기화
            sharedRoutineDTO.setIdx(routine.getIdx());
            sharedRoutineDTO.setItemName(routine.getItemName());
            sharedRoutineDTO.setItemDisc(routine.getItemDisc());
            sharedRoutineDTO.setStart(routine.getStart());
            sharedRoutineDTO.setEnd(routine.getEnd());
            sharedRoutineDTO.setLike_cnt(routine.getLike_cnt());

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
        Routine routine = routineRepository.findById(idx).orElseThrow(
                ()-> new IllegalArgumentException("수정 실패"));
        int cnt = routine.getLike_cnt() + 1;
        routine.setLike_cnt(cnt);

        routineRepository.save(routine);
    }
}
