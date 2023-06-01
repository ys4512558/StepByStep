package com.chageunchageun.chageunchageun.service;

import com.chageunchageun.chageunchageun.data.dto.Challenge.ChallengeDTO;
import com.chageunchageun.chageunchageun.data.entity.Challenge;
import com.chageunchageun.chageunchageun.data.entity.User;
import com.chageunchageun.chageunchageun.data.repository.ChallengeRepository;
import com.chageunchageun.chageunchageun.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    final private ChallengeRepository challengeRepository;
    final private UserRepository userRepository;


    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
    }

    /**
     * 이메일, 제목을 받아서
     * 새로운 챌린지 저장
     * @param email
     * @param title
     */
    public void saveChallenge(String email, String title){

        User user = userRepository.getReferenceById(email);

        Challenge challenge = new Challenge(user, title);

        challengeRepository.save(challenge);
    }

    /**
     * 이메일, 제목으로 검색하고
     * 현재 날짜로 완료 시간 설정 (업데이트)
     * @param email
     * @param title
     */
    public void ChallengeUpdate(String email, String title){

        LocalDate date = LocalDate.now();

        User user = userRepository.getReferenceById(email);

        Optional<Challenge> challengeOptional = challengeRepository.findTopByUserEmailAndTitle(user.getEmail(), title);

        Challenge challenge;

        if(challengeOptional.isPresent()){
            challenge = challengeOptional.get();

            challenge.setCompleteDate(date);

            challengeRepository.save(challenge);
        }
    }

    /**
     * 이메일을 받아서 completeDate가 Null이면
     * 완료되지 않았다고 판단 해당 데이터를 전부 가져와서
     * DTO로 변환 후 반환
     * @param email
     * @return
     */
    public List<ChallengeDTO> incompleteChallenge(String email){
        User user = userRepository.getReferenceById(email);

        List<Challenge> challenges = challengeRepository.findByUserEmailAndCompleteDateIsNull(user.getEmail());

        List<ChallengeDTO> challengeDTOS = new ArrayList<>();

        for(Challenge challenge : challenges){
            ChallengeDTO challengeDTO = new ChallengeDTO();
            challengeDTO.setTitle(challenge.getTitle());

            challengeDTOS.add(challengeDTO);
        }

        return challengeDTOS;
    }

    /**
     * 이메일을 받아서 completeDate가 있으면,
     * 완료되었다고 판단 후 완료된 데이터들 전부 반환
     * @param email
     * @return
     */
    public List<ChallengeDTO> completeChallenge(String email){
        User user = userRepository.getReferenceById(email);

        List<Challenge> challenges = challengeRepository.findByUserEmailAndCompleteDateIsNotNullOrderByCompleteDateDesc(user.getEmail());

        List<ChallengeDTO> challengeDTOS = new ArrayList<>();

        for(Challenge challenge : challenges){
            ChallengeDTO challengeDTO = new ChallengeDTO();
            challengeDTO.setTitle(challenge.getTitle());
            challengeDTO.setCompleteDate(challenge.getCompleteDate());

            challengeDTOS.add(challengeDTO);
        }

        return challengeDTOS;
    }

    public void DeleteChallenge(String email, String title){

        User user = userRepository.getReferenceById(email);

        Optional<Challenge> challengeOptional = challengeRepository.findTopByUserEmailAndTitle(user.getEmail(), title);

        if(challengeOptional.isPresent()){
            Challenge challenge = challengeOptional.get();

            challengeRepository.delete(challenge);
        }

    }
}
