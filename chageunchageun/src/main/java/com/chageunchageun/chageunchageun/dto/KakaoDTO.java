package com.chageunchageun.chageunchageun.dto;

public class KakaoDTO {
    private String k_name;
    private String k_email;
    private String k_image;

    public KakaoDTO() {
    }

    public KakaoDTO(String k_name, String k_email, String k_image) {
        this.k_name = k_name;
        this.k_email = k_email;
        this.k_image = k_image;
    }

    public String getK_name() {
        return k_name;
    }

    public void setK_name(String k_name) {
        this.k_name = k_name;
    }

    public String getK_email() {
        return k_email;
    }

    public void setK_email(String k_email) {
        this.k_email = k_email;
    }

    public String getK_image() {
        return k_image;
    }

    public void setK_image(String k_image) {
        this.k_image = k_image;
    }

}
