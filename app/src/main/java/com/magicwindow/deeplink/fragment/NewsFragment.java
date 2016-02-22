package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.adapter.ViewPageFragmentAdapter;
import com.magicwindow.deeplink.ui.PagerSlidingTabStrip;

import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by aaron on 15/11/27.
 */
public class NewsFragment extends BaseFragment {

    @InjectView(id=R.id.id_pagerSlidingTabStrip)
    PagerSlidingTabStrip mTabStrip;

    @InjectView(id=R.id.id_viewpager)
    ViewPager mViewPager;


    ViewPageFragmentAdapter mTabsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Injector.injectInto(this,view);

        initViews();

        return view;
    }

    private void initViews() {
        mTabsAdapter = new ViewPageFragmentAdapter(getFragmentManager(),
                mTabStrip, mViewPager);

        mViewPager.setAdapter(mTabsAdapter);
        mTabStrip.setViewPager(mViewPager);
    }
}
