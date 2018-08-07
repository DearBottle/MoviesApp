package com.bottle.moviesapp.net;

import com.bottle.moviesapp.bean.UserBean;

/**
 * Created by mengbaobao on 2018/8/4.
 */

public class Config {
    public static String url = "http://123.56.189.39/";
    private static UserBean userBean;
    private static String userToken;

    public static void setUserBean(UserBean userBean) {
        Config.userBean = userBean;
    }

    public static void setUserToken(String userToken) {
        Config.userToken = userToken;
    }

    public static void saveLoginUser(String loginName, String pwd) {

    }

    public static String getUserToken() {
        return userToken;
    }
}
