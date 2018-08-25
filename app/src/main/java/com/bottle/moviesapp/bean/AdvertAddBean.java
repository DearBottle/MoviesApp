package com.bottle.moviesapp.bean;

/**
 * Created by mengbaobao on 2018/8/25.
 */

public class AdvertAddBean extends BaseRequset {

    private int id;

    public AdvertAddBean(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
