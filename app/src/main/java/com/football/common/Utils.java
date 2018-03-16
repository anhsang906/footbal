package com.football.common;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.TypedValue;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Le Huu Quang on 4/29/15.
 * SunIvy JSC
 * quanglh
 */
public class Utils {
    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    public static final Integer NOTIFICATION_STATUS_SEEN = 1;
    public static String getText(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        return text;
    }

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    public static String getUriEncode(String url) {
        String urlEncoded = Uri.encode(url, ALLOWED_URI_CHARS);
        return urlEncoded;
    }

    public static float convertPxToDp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        float ret = (px) / scale;
        return ret;
    }

    public static float convertDpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        float ret = dp * scale;
        ;
        return ret;
    }

    public static float convertSpToPx(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        float ret = sp * scale;
        return ret;
    }

    public static boolean isUsernameValid(String username) {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isphoneValid(CharSequence phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

//    public static String convertTimestampToDate(Activity activity, String time) {
//
//        long lg;
//        lg = Long.valueOf(time);
//
//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(lg * 1000);
////        String date = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString();
//        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
//
//        TimeAgoUtils timeAgoUtils = new TimeAgoUtils(activity);
//        String timeAgo = timeAgoUtils.timeAgo(TimeUtils.getLongValueDate(date, ""));
//
//        return timeAgo;
//    }

    public static String convertTimestampToString( String time) {

        long lg;
        lg = Long.valueOf(time);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(lg * 1000);
//        String date = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString();
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();

        return date;
    }
}
