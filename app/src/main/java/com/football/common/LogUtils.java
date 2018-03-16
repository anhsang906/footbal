package com.football.common;

import android.util.Log;

/**
 * Created by Le Huu Quang on 10/16/17.
 * SunIvy JSC
 * quanglh
 */
public final class LogUtils {

    private static boolean IS_SHOW = true;


    public static void d(String tag, String msg) {
        if (IS_SHOW)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (IS_SHOW)
            Log.e(tag, msg);
    }
    public static void i(String tag, String msg) {
        if (IS_SHOW)
            Log.i(tag, msg);
    }
    public static void v(String tag, String msg) {
        if (IS_SHOW)
            Log.v(tag, msg);
    }
    public static void w(String tag, String msg) {
        if (IS_SHOW)
            Log.w(tag, msg);
    }
    public static void wtf(String tag, String msg) {
        if (IS_SHOW)
            Log.wtf(tag, msg);
    }
}
