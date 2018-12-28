package com.library.apple.food;

public class CartItem {

    public String getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(String cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    private String cart_item_id;

    public String getId_id() {
        return id_id;
    }

    public void setId_id(String id_id) {
        this.id_id = id_id;
    }

    private String id_id;
    private boolean vegnon_cart;
    private String title_cart;
    private String no_cart;
    private String price_cart;

    public CartItem() {
    }

    public CartItem(boolean vegnon_cart, String title_cart, String no_cart, String price_cart, String cart_item_id, String id_id) {
        this.vegnon_cart = vegnon_cart;
        this.title_cart = title_cart;
        this.no_cart = no_cart;
        this.price_cart = price_cart;
        this.cart_item_id = cart_item_id;
        this.id_id = id_id;
    }

    public boolean getVegnon_cart() {
        return vegnon_cart;
    }

    public void setVegnon_cart(boolean vegnon_cart) {
        this.vegnon_cart = vegnon_cart;
    }

    public String getTitle_cart() {
        return title_cart;
    }

    public void setTitle_cart(String title_cart) {
        this.title_cart = title_cart;
    }

    public String getNo_cart() {
        return no_cart;
    }

    public void setNo_cart(String no_cart) {
        this.no_cart = no_cart;
    }

    public String getPrice_cart() {
        return price_cart;
    }

    public void setPrice_cart(String price_cart) {
        this.price_cart = price_cart;
    }



}
