package com.football.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.football.R;

/**
 * Created by lehuuquang on 3/16/18.
 * Friday
 */

public class DetaileFragment extends Fragment {


    private static String Position = "POSITION";

    public static DetaileFragment newInstance(int position) {
        DetaileFragment fragment = new DetaileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Position, position);
        fragment.setArguments(bundle);
        return fragment;
    }
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        int position = getArguments().getInt(Position);

        if (mView == null) {
            mView = inflater.inflate(R.layout.detaile_fragment, container, false);
            initView(mView);

        } else {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    private void initView(View mView) {

    }
}
