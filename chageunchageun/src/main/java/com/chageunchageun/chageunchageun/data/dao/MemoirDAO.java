package com.chageunchageun.chageunchageun.data.dao;


import com.chageunchageun.chageunchageun.data.entity.Memoir;

import java.time.LocalDate;

public interface MemoirDAO {

    Memoir insertMemoir(Memoir memoir);

    Memoir selectMemoir(String  email, LocalDate date);

    Memoir updateMemoir(String email, String commend);

    void deleteMemoir(String email, String date);


}
