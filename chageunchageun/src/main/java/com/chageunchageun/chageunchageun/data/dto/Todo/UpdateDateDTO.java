package com.chageunchageun.chageunchageun.data.dto.Todo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDateDTO {
    private String todo_name;

    private String todo_disc;

    private LocalDate end_dateRp;
}
