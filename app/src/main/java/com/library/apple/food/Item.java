package com.library.apple.food;

public class Item {

    private String item_name;
    private String item_cuisine_name;
    private String item_price;
    private boolean item_veg;
    private String item_picture;
    private String item_id;
    private String item_restaurant;

    public Item() {
    }

    public Item(String item_name, String item_cuisine_name, String item_price, boolean item_veg, String item_picture, String item_id, String item_restaurant) {
        this.item_name = item_name;
        this.item_cuisine_name = item_cuisine_name;
        this.item_price = item_price;
        this.item_veg = item_veg;
        this.item_picture = item_picture;
        this.item_id = item_id;
        this.item_restaurant = item_restaurant;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_cuisine_name() {
        return item_cuisine_name;
    }

    public void setItem_cuisine_name(String item_cuisine_name) {
        this.item_cuisine_name = item_cuisine_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public boolean getItem_veg() {
        return item_veg;
    }

    public void setItem_veg(boolean item_veg) {
        this.item_veg = item_veg;
    }

    public String getItem_picture() {
        return item_picture;
    }

    public void setItem_picture(String item_picture) {
        this.item_picture = item_picture;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_restaurant() {
        return item_restaurant;
    }

    public void setItem_restaurant(String item_restaurant) {
        this.item_restaurant = item_restaurant;
    }





}
