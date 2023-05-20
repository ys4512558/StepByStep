package com.chageunchageun.chageunchageun.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private String todoName;
    private String todoDisc;
    private LocalDate endDate;
    private LocalDate startDate;
    private Boolean oneOff;
}
