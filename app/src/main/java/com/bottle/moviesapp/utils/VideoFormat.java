package com.bottle.moviesapp.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by mengbaobao on 2018/8/4.
 */

/**
 * Created by Administrator on 2016/1/14.
 */
public class VideoFormat {

    public static String encryptToCache(Context context, String strFile) {
        try {
            File newfile = new File(getDiskCachePath(context), strFile.substring(strFile.lastIndexOf("/") + 1));
            if (newfile.exists()) {
                newfile.delete();
                //return newfile.getAbsolutePath();
            }
            fileCopy(strFile, newfile.getAbsolutePath());
            return encrypt(newfile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {

        }
    }

    /**
     * 加解密
     *
     * @param strFile 源文件绝对路径
     * @return
     */
    public static String encrypt(String strFile) {
        int len = 105;
        try {
            File f = new File(strFile);
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            long totalLen = raf.length();

            if (totalLen < 105) {
                len = (int) totalLen;
            }

            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(
                    FileChannel.MapMode.READ_WRITE, 0, len);
            byte tmp;
            for (int i = 0; i < len; ++i) {
                byte rawByte = buffer.get(i);
                tmp = (byte) (rawByte ^ i);
                buffer.put(i, tmp);
            }
            buffer.force();
            buffer.clear();
            channel.close();
            raf.close();
            return strFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    public static boolean fileCopy(String oldFilePath, String newFilePath) throws IOException {
        //如果原文件不存在
        if (fileExists(oldFilePath) == false) {
            return false;
        }
        //获得原文件流
        FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
        byte[] data = new byte[1024];
        //输出流
        FileOutputStream outputStream = new FileOutputStream(new File(newFilePath));
        //开始处理流
        while (inputStream.read(data) != -1) {
            outputStream.write(data);
        }
        inputStream.close();
        outputStream.close();
        return true;
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

}