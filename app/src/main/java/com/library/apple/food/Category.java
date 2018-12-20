package com.library.apple.food;

public class Category {

    private String cate_id;
    private String cate_name;
    private String cate_picture;

    public Category() {
    }

    public Category(String cate_id, String cate_name, String cate_picture) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.cate_picture = cate_picture;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_picture() {
        return cate_picture;
    }

    public void setCate_picture(String cate_picture) {
        this.cate_picture = cate_picture;
    }






}
