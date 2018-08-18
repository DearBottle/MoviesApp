package com.bottle.moviesapp.bean;

/**
 * Created by mengbaobao on 2018/8/18.
 */

public class RequsetPermission extends BaseRequset {
    private String fileName;
    private String sha256;

    public RequsetPermission(String fileName, String sha256) {
        this.fileName = fileName;
        this.sha256 = sha256;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }
}
