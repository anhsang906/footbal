package com.football.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.football.FApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class CommonActivity {
    private static final String TAG = "CommonActivity";
    public Context mContext;
    public Activity activity;
    private static ProgressDialog pDialog;


    public CommonActivity(Context mContext) {
        super();
        this.mContext = mContext;
        this.activity = (Activity) mContext;

    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory == null) {
            return;
        }
        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                for (File child : fileOrDirectory.listFiles()) {
                    try {
                        boolean isDelete = child.delete();
                        Log.d("SaveRecordActivity", "isDelete = " + isDelete);

                    } catch (Exception e) {
                        Log.d("SaveRecordActivity", "e = " + e.toString());
                        e.printStackTrace();
                    }
                }
                try {
                    fileOrDirectory.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                boolean isDelete2 = fileOrDirectory.delete();
                Log.d("SaveRecordActivity", "fileOrDirectory.delete() = " + isDelete2);
            }
        } else {
            Log.d("SaveRecordActivity", "file not exists");
        }
    }

    public static void deleteRecursive(String str) {
        File fileOrDirectory = new File(str);
        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                if (fileOrDirectory.listFiles() != null) {
                    try {
                        if ((fileOrDirectory.listFiles()).length > 0) {
                            for (File child : fileOrDirectory.listFiles()) {
                                try {
                                    boolean isDelete = child.delete();
                                    Log.d("SaveRecordActivity", "isDelete = " + isDelete);
                                } catch (Exception e) {
                                    Log.d("SaveRecordActivity", "e = " + e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    fileOrDirectory.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                boolean isDelete2 = fileOrDirectory.delete();
                Log.d("SaveRecordActivity", "fileOrDirectory.delete() = " + isDelete2);
            }
        } else {
            Log.d("SaveRecordActivity", "file not exists");
        }
    }

    public static void deleteRecur(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }


    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = FApp.mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = FApp.mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static int getInt(String key) {
        int value = 0;
        value = FApp.mSharedPreferences.getInt(key, 0);
        return value;
    }

    public static String getString(String key) {
        String value = "";
        value = FApp.mSharedPreferences.getString(key, "");
        return value;
    }

    private boolean isShowLog = true;

    public static void showLog(String tag, String mess) {
        Log.d(tag, mess);
    }

    public boolean isNetworkOnline() {
        boolean val = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            }
            final NetworkInfo mobile = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null && mobile.isAvailable() && mobile.isConnected()) {
                val = true;
            }
            /*
             * else { final android.net.NetworkInfo wifi = connectivityManager
             * .getNetworkInfo(ConnectivityManager.TYPE_WIFI); if (wifi != null
             * && wifi.isAvailable() && wifi.isConnected()) { val = true; } }
             */
        } catch (Exception e) {
            Log.e("Exception", e.toString(), e);
        }

        return val;

    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = FApp.mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        try {
            return FApp.mSharedPreferences.getBoolean(key, false);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNetworkConnected(Activity activity) {
        boolean val = false;
        if (activity == null) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            }
            final NetworkInfo mobile = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null && mobile.isAvailable() && mobile.isConnected()) {
                val = true;
            } else {
                final NetworkInfo wifi = connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifi != null && wifi.isAvailable() && wifi.isConnected()) {
                    val = true;
                }
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString(), e);
        }
        return val;
    }

    public static boolean hasInternetAccess(Activity context) {
        if (isNetworkConnected(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e(TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(TAG, "No network available!");
        }
        return false;
    }


    public static void showToast(Context mContext, String mess) {
        Toast.makeText(mContext, mess, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackgroundView(Context context, View view, Drawable draw) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {

            view.setBackgroundDrawable(draw);
        } else {
            view.setBackground(draw);
        }
    }


    // SHOW KEYBOARD
    public static void showKeyBoard(View view, Context context) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String parseString(JSONObject jsonObject, String key) {
        String value = "";
        try {
            value = jsonObject.getString(key);
            return value;
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }

    }

    public static JSONArray parseJsonArray(JSONObject jsonObject, String following) {
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(following);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONObject parseJSONObject(JSONObject json, String key) {
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(key);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }

    }

    public static int parseInt(JSONObject jsonObject, String key) {
        int value = 0;
        try {
            value = jsonObject.getInt(key);
            return value;
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }
    }

    public static boolean parseBoolean(JSONObject jsonObject, String key) {
        boolean value = false;
        try {
            value = jsonObject.getBoolean(key);
            return value;
        } catch (JSONException e) {
            e.printStackTrace();
            return value;
        }
    }


    private static void putShared(SharedPreferences prefs, int showAds, String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, showAds);
        editor.apply();
    }

    public static String ParseMsToMinutes(int Ms) {
        try {
            return ("" + Integer.toString(Ms / (1000 * 60)) + ":" + Integer.toString((Ms % (1000 * 60)) / 1000));

        } catch (Exception e) {
            return "";
        }
    }

    private static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        if (context != null) {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String ParsetoDeltaDay(String DateCreate) {
        try {
            long longDateCreate = Long.parseLong(DateCreate);
            long CurrentDay = System.currentTimeMillis() / 1000;
            long LongDelta = CurrentDay - longDateCreate;
            long DeltaDay = LongDelta / (24 * 60 * 60);

            return Long.toString(DeltaDay);

        } catch (Exception e) {
            return "0";
        }
    }


    private static String readStream(HttpURLConnection conn) {
        InputStream in = null;
        try {
            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        conn.disconnect();
        return builder.toString();
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

    public static String convetNumber(int totalNumber) {
        String txt = "";
        int n1 = totalNumber / 1000;
        int n2 = totalNumber % 1000;
        String donvi = "";
        if (totalNumber < 1000) {
            return String.valueOf(totalNumber);
        } else if (totalNumber < 1000000) {
            donvi = "K";
        } else if (totalNumber < 1000000000) {
            donvi = "M";
        } else {
            donvi = "B";
        }
        if (n2 < 50) {
            txt = String.valueOf(n1) + donvi;
        } else if (n2 < 150) {
            txt = String.valueOf(n1) + ".1" + donvi;
        } else if (n2 < 250) {
            txt = String.valueOf(n1) + ".2" + donvi;
        } else if (n2 < 350) {
            txt = String.valueOf(n1) + ".3" + donvi;
        } else if (n2 < 450) {
            txt = String.valueOf(n1) + ".4" + donvi;
        } else if (n2 < 550) {
            txt = String.valueOf(n1) + ".5" + donvi;
        } else if (n2 < 650) {
            txt = String.valueOf(n1) + ".6" + donvi;
        } else if (n2 < 750) {
            txt = String.valueOf(n1) + ".7" + donvi;
        } else if (n2 < 850) {
            txt = String.valueOf(n1) + ".8" + donvi;
        } else if (n2 < 950) {
            txt = String.valueOf(n1) + ".9" + donvi;
        }
        return txt;
    }

    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }
}
