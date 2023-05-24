package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.entity.Routine;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.dto.RoutineDTO;
import com.chageunchageun.chageunchageun.data.dto.RoutinesDTO;
import com.chageunchageun.chageunchageun.data.entity.SharedRoutine;
import com.chageunchageun.chageunchageun.data.repository.RoutineRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RoutineService {

    private final UserRepository userRepository;

    private final RoutineRepository routineRepository;

    @Autowired
    public RoutineService(UserRepository userRepository, RoutineRepository routineRepository) {
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
    }


    /**
     * 클라이언트로부터 이미지를 받아 저장하는 메서드
     * email을 primary 키로하여
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

    public List<HashMap<String, Object>> jsonFile (String jsonData) {

        final String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/test.json";

        JSONParser parser = new JSONParser();

        List<HashMap<String,Object>> list = new ArrayList<HashMap<String ,Object>>();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

            //1. JSONArray의 Routines부분만 저장
            JSONArray routinesArray = (JSONArray) jsonObject.get("Routines");

            for (Object object : routinesArray) {
                JSONObject routineObject = (JSONObject) object;

                String itemDisc = (String) routineObject.get("itemDisc");
                String itemName = (String) routineObject.get("itemName");
                String start = (String) routineObject.get("start");
                String end = (String) routineObject.get("end");

                HashMap<String ,Object> map = new HashMap<String , Object>();
                map.put("itemDisc", itemDisc);
                map.put("itemName", itemName);
                map.put("start", start);
                map.put("end", end);

                list.add(map);
            }
            FileWriter file = new FileWriter(dir);
            //1.
            file.write(routinesArray.toJSONString());
            //file.write(jsonObject.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(dir);

        return list;
    }

    public void jsonFile2 (String jsonData) {

        //final String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/test.json";
        final String dir =
                "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/test.json";
        JSONParser parser = new JSONParser();

        List<RoutineDTO> list = new ArrayList<RoutineDTO>();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

            //1. JSONArray의 Routines부분만 저장
            JSONArray routinesArray = (JSONArray) jsonObject.get("Routine");

            String email = (String) jsonObject.get("email");
            String day = (String) jsonObject.get("day");

            for (Object object : routinesArray) {
                JSONObject routineObject = (JSONObject) object;

                String itemDisc = (String) routineObject.get("itemDisc");
                String itemName = (String) routineObject.get("itemName");
                String start = (String) routineObject.get("start");
                String end = (String) routineObject.get("end");

                RoutineDTO routineDTO = new RoutineDTO();
                routineDTO.setItemDisc(itemDisc);
                routineDTO.setItemName(itemName);
                routineDTO.setEnd(end);
                routineDTO.setStart(start);

                list.add(routineDTO);
            }
            FileWriter file = new FileWriter(dir);

            file.write(routinesArray.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 문자열 routines를 @RequestBody에서 받음
     * Json으로 파싱 후 파일에 저장
     * 파일은 Json속 email, day를 통해 폴더 해당 폴더에 저장
     * @param routines
     */
    public void saveRoutines(String routines){
        //String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/";
        String dir =
                "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/";

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(routines);
            String email = (String) jsonObject.get("email");
            String day = (String) jsonObject.get("day");

            dir +=  email + "/Memoir/" + day + "/test.json";
            FileWriter file = new FileWriter(dir);

            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //.json파일 읽어서 다시 전송
    public RoutinesDTO selectRoutines(String emailParam, String dayParam){
        //final String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" + emailParam + "/Routine/" + dayParam + "/test.json";

        final String dir = "C:/Users/ys451/OneDrive/바탕 화면/종합설계/차근차근/chageunchageun/src/main/resources/User/"
                + emailParam + "/Routine/" + dayParam + "/test.json";

        JSONParser parser = new JSONParser();
        JSONArray routinesArray;
        String email;
        String day;
        try {
            FileReader fileReader = new FileReader(dir);
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            routinesArray = (JSONArray) jsonObject.get("Routine");
            email = (String) jsonObject.get("email");
            day = (String) jsonObject.get("day");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<RoutineDTO> routines = new ArrayList<RoutineDTO>();

        for (Object object : routinesArray) {
            JSONObject routineObject = (JSONObject) object;

            String itemDisc = (String) routineObject.get("itemDisc");
            String itemName = (String) routineObject.get("itemName");
            String start = (String) routineObject.get("start");
            String end = (String) routineObject.get("end");

            RoutineDTO routineDTO = new RoutineDTO();
            routineDTO.setItemDisc(itemDisc);
            routineDTO.setItemName(itemName);
            routineDTO.setEnd(end);
            routineDTO.setStart(start);

            routines.add(routineDTO);
        }

        RoutinesDTO routinesDTO = new RoutinesDTO();
        routinesDTO.setRoutines(routines);
        routinesDTO.setEmail(email);
        routinesDTO.setDay(day);

        return routinesDTO;
    }

    /**
     * 문자열 routines를 @RequestBody에서 받음
     * Json으로 파싱 후 DB에 저장
     * @param routines
     */
    public void saveRoutine(String routines){
        System.out.println(routines);
        JSONParser parser = new JSONParser();

        String email = null;
        String day = null;
        JSONArray routineArray = null;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(routines);
            email = (String) jsonObject.get("email");
            day = (String) jsonObject.get("day");
            routineArray = (JSONArray) jsonObject.get("Routine");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = userRepository.getReferenceById(email);

        user.setRoutines(parseJsonArray(routineArray));

        for(Routine routine : user.getRoutines()){
            routine.setDay(day);
            routine.setUser(user);
            routineRepository.save(routine);
        }

    }

    /**
     * JSONArray를 엔티티 List로 변환(파싱) 후 반환
     * @param routineArray
     * @return
     */
    public List<Routine> parseJsonArray(JSONArray routineArray){

        //JSONArray에 들어갈 내용들
        String itemName;
        String itemDisc;
        String start;
        String end;

        List<Routine> list = new ArrayList<Routine>();
        for(Object object : routineArray){
            JSONObject routineObject = (JSONObject) object;

            itemName = (String) routineObject.get("itemName");
            itemDisc = (String) routineObject.get("itemDisc");
            start = (String) routineObject.get("start");
            end = (String) routineObject.get("end");

            Routine routine = new Routine();
            routine.setItemName(itemName);
            routine.setItemDisc(itemDisc);
            routine.setStart(start);
            routine.setEnd(end);

            list.add(routine);
        }
        return list;
    }

    /**
     * DB에서 이메일, 요일로 검색
     * 해당 데이터 RoutineDTO에 저장
     * ArrayList<RoutineDTO>에 저장
     * RoutinesDTO에 이메일, 요일, List<RoutineDTO>저장
     * RoutinesDTO 리턴
     * @param emailParam
     * @param dayParam
     * @return
     */
    public RoutinesDTO selectRoutine(String emailParam, String dayParam){

        List<Routine> routines = routineRepository.findByUserEmailAndDay(emailParam, dayParam);

        List<RoutineDTO> routineDTOS = new ArrayList<RoutineDTO>();

        for (Routine routine : routines) {

            RoutineDTO routineDTO = new RoutineDTO();
            routineDTO.setItemDisc(routine.getItemDisc());
            routineDTO.setItemName(routine.getItemName());
            routineDTO.setEnd(routine.getEnd());
            routineDTO.setStart(routine.getStart());

            routineDTOS.add(routineDTO);
        }

        RoutinesDTO routinesDTO = new RoutinesDTO();
        routinesDTO.setRoutines(routineDTOS);
        routinesDTO.setEmail(emailParam);
        routinesDTO.setDay(dayParam);

        return routinesDTO;
    }

    public void updateRoutine(String updateRoutine) {

        JSONParser parser = new JSONParser();

        String email;
        String item_name;
        String item_disc;
        String day;

        String item_nameRp;
        String item_discRp;
        String startRp;
        String endRp;


        try {
            JSONObject jsonObject = (JSONObject) parser.parse(updateRoutine);

            email = (String) jsonObject.get("email");
            item_name = (String) jsonObject.get("item_name");
            item_disc = (String) jsonObject.get("item_disc");
            day = (String) jsonObject.get("day");
            item_nameRp = (String) jsonObject.get("item_nameRp");
            item_discRp = (String) jsonObject.get("item_discRp");
            startRp = (String) jsonObject.get("startRp");
            endRp = (String) jsonObject.get("endRp");


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Optional<Routine> routineOptionaline = routineRepository.findByUserEmailAndItemNameAndItemDiscAndDay(email, item_name, item_disc, day);

        Routine routine = new Routine();

        if(routineOptionaline.isPresent()){
            routine = routineOptionaline.get();
        }

        routine.setItemName(item_nameRp);
        routine.setItemDisc(item_discRp);
        routine.setStart(startRp);
        routine.setEnd(endRp);

        routineRepository.save(routine);

    }

}
