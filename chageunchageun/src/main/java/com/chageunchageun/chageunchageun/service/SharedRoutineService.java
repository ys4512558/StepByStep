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
    public List<SharedRoutineDTO> selectSharedRoutine(String mbtiParam, String emailParam){
        List<Integer> sharedIdx = routineRepository.getIdxWithUserMbtiAndEmail(mbtiParam, emailParam);

        System.out.println("mbti = " + mbtiParam);
        System.out.println("email = " + emailParam);
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
            int randNum = random.nextInt(sharedIdx.size());
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

        for(SharedRoutineDTO s : sharedRoutineDTOS){
            System.out.println(s.getIdx());
            System.out.println(s.getItemName());
            System.out.println(s.getItemDisc());
            System.out.println(s.getStart());
            System.out.println(s.getEnd());
        }

        return sharedRoutineDTOS;
    }
    /**
     * 루틴 공유시 count++ 메서드
     * @param idx
     */
    @Transactional
    public void plusLikeRoutine(Integer idx) {
        Routine routine = routineRepository.findById(idx).orElseThrow(
                ()-> new IllegalArgumentException("수정 실패"));
        int cnt = routine.getLike_cnt() + 1;
        routine.setLike_cnt(cnt);

        routineRepository.save(routine);
    }

    @Transactional
    public void minusLikeRoutine(Integer idx) {
        Routine routine = routineRepository.findById(idx).orElseThrow(
                ()-> new IllegalArgumentException("수정 실패"));
        int cnt = routine.getLike_cnt() - 1;
        routine.setLike_cnt(cnt);

        routineRepository.save(routine);
    }
}
