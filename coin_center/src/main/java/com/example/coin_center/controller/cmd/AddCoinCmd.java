package com.example.coin_center.controller.cmd;

import java.util.Date;

public class AddCoinCmd {
    private String code;
    private int price;
    private int store;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }
}
