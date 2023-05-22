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
public class MemoirPreviewDTO {
    private LocalDate memoirDate;
    private String previewImg;
}
