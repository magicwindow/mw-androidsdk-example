package com.magicwindow.deeplink.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.magicwindow.deeplink.ui.PagerSlidingTabStrip;
import com.magicwindow.deeplink.fragment.NewsDetailFragment;


@SuppressLint("Recycle")
public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter {


    private final int HOME = 0;
    private final int CATEGORY = 1;
    private final int FIND = 2;
    private final int COUNT = 3;

    private final Context mContext;
    protected PagerSlidingTabStrip mPagerStrip;
    private final ViewPager mViewPager;
    private String[] titles = {"互联网", "体育", "娱乐"};
    public ViewPageFragmentAdapter(FragmentManager fm,
                                   PagerSlidingTabStrip pageStrip, ViewPager pager) {
        super(fm);
        mContext = pager.getContext();
        mPagerStrip = pageStrip;
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mPagerStrip.setViewPager(mViewPager);
    }


    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if (position> 2){
            position = 2;
        }
        return NewsDetailFragment.newInstance(position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}