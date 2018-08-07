package com.bottle.moviesapp.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * 自定义的Toast
 * normal:普通的Toast,不含图标
 * success: 成功信息的Toast
 * info:    提示信息的Toast
 * warning: 警告信息的Toast
 * error:   错误信息的Toast
 */
public class ToastUtil {

    private ToastUtil() {
    }

    public static void showToast(Context context, String txt, int duration) {
        Toast.makeText(context, txt, duration).show();
    }

    public static void showToast(Context context, String txt) {
        showToast(context, txt, Toast.LENGTH_SHORT);
    }


}
