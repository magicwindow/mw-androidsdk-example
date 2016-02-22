package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.app.BaseFragment;

/**
 * Created by aaron on 15/11/27.
 */
public class HouseFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(com.magicwindow.deeplink.R.layout.fragment_house, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        setScreenPageLimit();
//        onSetupTabAdapter(mTabsAdapter);
        // if (savedInstanceState != null) {
        // int pos = savedInstanceState.getInt("position");
        // mViewPager.setCurrentItem(pos, true);
        // }
    }

}
