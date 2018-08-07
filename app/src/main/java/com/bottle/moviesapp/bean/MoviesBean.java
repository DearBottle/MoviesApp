package com.bottle.moviesapp.bean;

import android.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by mengbaobao on 2018/7/28.
 */

public class MoviesBean implements Serializable {
    private long size;
    private long time;
    private String thumbPath;
    private String filePath;
    private String title;
    private String mimeType;

    public MoviesBean() {

    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        this.size = getFileSize(filePath);
        this.time = getTime(filePath);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public long getSize() {
        return size;
    }

    private long getTime(String filePath) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(filePath);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long duration = player.getDuration();//获取音频的时间
        player.release();//记得释放资源
        return duration;
    }

    private static long getFileSize(String filePath) {
        long size = 0;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            }
        } catch (Exception e) {

        }
        return size;
    }

}
