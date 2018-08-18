package com.bottle.moviesapp.bean;

import com.bottle.moviesapp.net.Config;

/**
 * Created by mengbaobao on 2018/8/18.
 */

public class BaseRequset {
    private Long TimeStamp = System.currentTimeMillis();
    private int ApiVersion = 1;
    private String Token = Config.getUserToken();
}
