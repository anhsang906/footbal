package com.football;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by lehuuquang on 3/15/18.
 */

public class FApp extends MultiDexApplication {

    private static FApp instance = null;

    // Application context for singleton classes
    private static Context ctx = null;
    private static AppCompatActivity activity= null;


    public static SharedPreferences mSharedPreferences;

    public static FApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    public static String getDeviceID() {
        SharedPreferences prefs = getStaticApplicationContext().getSharedPreferences("device", 0);
        String id = prefs.getString("id", null);
        if (id == null) {
            id = UUID.randomUUID().toString();
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("id", id);
            edit.commit();
        }
        return id;
    }


    // Grant acces for the application context from outside
    // This should NOT be null after application launch
    public static Context getStaticApplicationContext() {
        if(ctx == null) return getActivity();
        return ctx;
    }
    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void closeSoftKeyboard(View view) {
        if (view == null) return;
        InputMethodManager imm = (InputMethodManager) getStaticApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void openSoftKeyboard(View view) {
        if (view == null) return;
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getStaticApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static String getSDKCodeName() {
        String codeName = "";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (
                    Exception e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                codeName = fieldName;
            }
        }

        return codeName;
    }
}
