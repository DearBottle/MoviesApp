package com.bottle.moviesapp.bean;

import android.annotation.SuppressLint;


/**
 * Created by mengbaobao on 2017/10/30.
 */

public class RequestUserToken extends BaseRequset{
    private String loginName;
    private String loginPassword;


    @SuppressLint({"MissingPermission", "HardwareIds"})
    public RequestUserToken(String loginName, String loginPassword) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;

    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
