package com.bottle.moviesapp.bean;

/**
 * Created by mengbaobao on 2018/8/18.
 */

public class PayBean {
    private boolean isPay;//是否已经支付
    private int price;//影片价钱(币种:人民币,单位:分),如10000表示100.00人民币
    private int vid;//视频id

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return vid;
    }

    public void setId(int id) {
        this.vid = id;
    }
}
