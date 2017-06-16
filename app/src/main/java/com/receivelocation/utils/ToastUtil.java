package com.receivelocation.utils;

import android.widget.Toast;

import com.receivelocation.MyApp;

/**
 * @createAuthor zfb
 * @createTime 2016/9/23${Time}
 * @describe ${ToastUtil}
 */

public class ToastUtil {

    private static Toast toast;

    public static void show(String content) {
        if (toast == null) {
            toast = Toast.makeText(MyApp.getInstance(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}