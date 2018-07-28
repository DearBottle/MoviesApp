package com.bottle.moviesapp.bean;

/**
 * Created by mengbaobao on 2018/7/28.
 */

public class MoviesBean {
    private String name;
    private long size;
    private long time;

    public MoviesBean(String name, long size, long time) {
        this.name = name;
        this.size = size;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
