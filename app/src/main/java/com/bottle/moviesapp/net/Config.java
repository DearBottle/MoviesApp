package com.bottle.moviesapp.net;

import android.telephony.TelephonyManager;

import com.bottle.moviesapp.base.BaseApplication;
import com.bottle.moviesapp.bean.UserBean;
import com.bottle.moviesapp.utils.TextUtil;

/**
 * Created by mengbaobao on 2018/8/4.
 */

public class Config {
    public static String url = "http://www.magicdy.com/";
    //public static String url = "http://video.xihaikeji.com/";
    public static String videoDetail = url + "Movie/Details/";
    private static int apiVersion = 1;
    private static String imei;


    private static UserBean userBean;
    private static String userToken;

    public static void setUserBean(UserBean userBean) {
        Config.userBean = userBean;
    }

    public static UserBean getUserBean() {
        return userBean;
    }

    public static void setUserToken(String userToken) {
        Config.userToken = userToken;
    }

    public static void saveLoginUser(String loginName, String pwd) {

    }

    public static String getUserToken() {
        return userToken;
    }


    public static String getIMEI() {
        if (!TextUtil.isValidate(imei)) {
            TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getContext().getSystemService(BaseApplication.getContext().TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        }
        return imei;
    }

    public static int getApiVersion() {
        return apiVersion;
    }
}
