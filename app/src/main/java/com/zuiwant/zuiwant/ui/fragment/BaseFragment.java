package com.zuiwant.zuiwant.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by yw on 2015/5/3.
 */
public class BaseFragment extends Fragment  {

    protected boolean mIsLogin;


    public static interface BackHandledInterface {
        public abstract void setSelectedFragment(BaseFragment selectedFragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onBackPressed() {
        return false;
    }
}
