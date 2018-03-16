package com.football.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.football.FApp;

/**
 * Created by lehuuquang on 3/15/18.
 */

public class OfflineManager { private BroadcastReceiver broadcastReceiver;
    private OnConnectionChangedListener onConnectionChangedListener = null;

    public OfflineManager() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (onConnectionChangedListener != null) {
                    onConnectionChangedListener.onConnectionChanged(isNetworkAvailable());
                }
            }
        };

        FApp.getStaticApplicationContext().registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void destroy() {
        onConnectionChangedListener = null;
        try {
            FApp.getStaticApplicationContext().unregisterReceiver(this.broadcastReceiver);
        }
        catch (IllegalArgumentException ignore) {
            // broadcastreciever was already unregistered
        }
        finally {
            broadcastReceiver = null;
        }
    }

    public OnConnectionChangedListener getOnConnectionChangedListener() {
        return onConnectionChangedListener;
    }

    public void setOnConnectionChangedListener(OnConnectionChangedListener onConnectionChangedListener) {
        this.onConnectionChangedListener = onConnectionChangedListener;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) FApp.getStaticApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public interface OnConnectionChangedListener {
        void onConnectionChanged(boolean isNetworkAvailable);
    }
}
