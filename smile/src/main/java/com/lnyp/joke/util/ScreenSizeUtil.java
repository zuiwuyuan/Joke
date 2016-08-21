package com.lnyp.joke.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 屏幕工具类
 */
public class ScreenSizeUtil {

    public static int getScreenWidth(Activity activity) {

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenWidth = metric.widthPixels;

        return screenWidth;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int screenHeight = metric.heightPixels;

        return screenHeight;
    }

}
