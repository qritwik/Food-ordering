package com.library.apple.food;

public class Place {

    String add_id;
    String add_line_1;
    String add_phone_number;
    String add_phone_verified;
    boolean add_selected;
    String add_location;

    public Place() {
    }

    public Place(String add_id, String add_line_1, String add_phone_number, String add_phone_verified, boolean add_selected, String add_location) {
        this.add_id = add_id;
        this.add_line_1 = add_line_1;
        this.add_phone_number = add_phone_number;
        this.add_phone_verified = add_phone_verified;
        this.add_selected = add_selected;
        this.add_location = add_location;
    }


    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getAdd_line_1() {
        return add_line_1;
    }

    public void setAdd_line_1(String add_line_1) {
        this.add_line_1 = add_line_1;
    }

    public String getAdd_phone_number() {
        return add_phone_number;
    }

    public void setAdd_phone_number(String add_phone_number) {
        this.add_phone_number = add_phone_number;
    }

    public String getAdd_phone_verified() {
        return add_phone_verified;
    }

    public void setAdd_phone_verified(String add_phone_verified) {
        this.add_phone_verified = add_phone_verified;
    }

    public boolean getAdd_selected() {
        return add_selected;
    }

    public void setAdd_selected(boolean add_selected) {
        this.add_selected = add_selected;
    }

    public String getAdd_location() {
        return add_location;
    }

    public void setAdd_location(String add_location) {
        this.add_location = add_location;
    }




}
