package com.football.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.football.R;

/**
 * Created by Le Huu Quang on 10/29/2015.
 * SunIvy JSC
 * quanglh
 */
public class ReplaceFragment {
    private static final String TAG = "ReplaceFragment";

    public void openOutsideTabFragment(Class<? extends Fragment> fragmentClass, Bundle... bundles) {

        Bundle bundle;
        if (bundles != null && bundles.length >= 1) {
            bundle = bundles[0];
        } else {
            bundle = new Bundle();
        }

//        openFragment(fragmentClass, bundle);
    }

    public void replace(FragmentManager manager, Fragment fragmentClazz,
                        Bundle args) {
        String backStateName = fragmentClazz.getClass().getName();//Class();

//        transaction.commitAllowingStateLoss();
        boolean fragmentPopped = false;
        try {
            fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        manager.popBackStack(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        Log.d(TAG, "backStateName = " + backStateName);
        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content, fragmentClazz);
//            transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            transaction.addToBackStack(backStateName);
            try {
//          Commit the transaction
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replace2(FragmentManager manager, Fragment fragmentClazz,
                         Bundle args) {
        String backStateName = fragmentClazz.getClass().getName();//Class();

        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        Log.d(TAG, "fragmentPopped = " + fragmentPopped);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, fragmentClazz);
        transaction.addToBackStack(backStateName);
        transaction.commit();
    }
}
