package com.bottle.moviesapp.net;

import java.io.Serializable;

/**
 * 服务器响应
 */

public class DxResponse<T> implements Serializable {
    private String message;
    private int code;
    private T data;

    public DxResponse(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DxResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public boolean isSucc() {
        return code == 0;
    }
}
