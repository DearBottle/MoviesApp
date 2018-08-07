package com.bottle.moviesapp.net;

/**
 * Created by admin on 2018/6/4.
 */

public class DxServerException extends RuntimeException {

    private final int code;
    private final String msg;

    public DxServerException(int errorCode, String msg) {
        super("服务器错误，错误码:" + errorCode + "，错误原因:" + msg);
        this.code = errorCode;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}