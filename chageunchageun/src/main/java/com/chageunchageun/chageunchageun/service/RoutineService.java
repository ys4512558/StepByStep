package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.RoutineDTO;
import com.chageunchageun.chageunchageun.data.dto.RoutinesDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RoutineService {


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

                /*
                System.out.println("Item Disc: " + itemDisc);
                System.out.println("Item Name: " + itemName);
                System.out.println("Start: " + start);
                System.out.println("End: " + end);
                */
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

        final String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/test.json";

        JSONParser parser = new JSONParser();

        List<RoutineDTO> list = new ArrayList<RoutineDTO>();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

            //1. JSONArray의 Routines부분만 저장
            JSONArray routinesArray = (JSONArray) jsonObject.get("Routine");
            System.out.println(routinesArray);

            String email = (String) jsonObject.get("email");
            String day = (String) jsonObject.get("day");
            System.out.println(day);

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
        String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/";

        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(routines);
            String email = (String) jsonObject.get("email");
            String day = (String) jsonObject.get("day");

            System.out.println(jsonObject);

            dir +=  email + "/Routine/" + day + "/test.json";
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
        final String dir = "C:/Users/ys451/OneDrive/바탕 화면/4학년 폴더/차근차근/UserFile/" +
                emailParam + "/Routine/" + dayParam + "/test.json";

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
}
