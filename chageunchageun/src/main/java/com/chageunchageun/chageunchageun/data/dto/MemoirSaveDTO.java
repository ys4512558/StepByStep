package com.chageunchageun.chageunchageun.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoirSaveDTO {

    private String email;

    private LocalDate date;

    private String itemName;

    private MultipartFile img;
}
