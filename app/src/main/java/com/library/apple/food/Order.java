package com.library.apple.food;

public class Order {

    private String order_res_name;
    private String order_item_price;
    private String order_home_name;
    private String order_order_id;

    public Order() {
    }


    public Order(String order_res_name, String order_item_price, String order_home_name, String order_order_id) {
        this.order_res_name = order_res_name;
        this.order_item_price = order_item_price;
        this.order_home_name = order_home_name;
        this.order_order_id = order_order_id;
    }

    public String getOrder_res_name() {
        return order_res_name;
    }

    public void setOrder_res_name(String order_res_name) {
        this.order_res_name = order_res_name;
    }

    public String getOrder_item_price() {
        return order_item_price;
    }

    public void setOrder_item_price(String order_item_price) {
        this.order_item_price = order_item_price;
    }

    public String getOrder_home_name() {
        return order_home_name;
    }

    public void setOrder_home_name(String order_home_name) {
        this.order_home_name = order_home_name;
    }

    public String getOrder_order_id() {
        return order_order_id;
    }

    public void setOrder_order_id(String order_order_id) {
        this.order_order_id = order_order_id;
    }








}
