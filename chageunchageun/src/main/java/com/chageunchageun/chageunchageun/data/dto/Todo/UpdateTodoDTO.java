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
public class UpdateTodoDTO {

    private String todo_name;

    private String todo_disc;

    private LocalDate end_date;

    private LocalDate start_date;

    private Boolean one_off;

    private String todo_nameRp;

    private String todo_discRp;

    private LocalDate end_dateRp;

    private LocalDate start_dateRp;

    private Boolean one_offRp;

}
