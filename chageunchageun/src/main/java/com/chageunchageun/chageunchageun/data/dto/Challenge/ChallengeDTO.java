package com.chageunchageun.chageunchageun.data.dto.Challenge;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {

    String title;

    LocalDate completeDate;

}
