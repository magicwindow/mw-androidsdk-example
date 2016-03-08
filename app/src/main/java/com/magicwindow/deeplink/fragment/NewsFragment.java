package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.ViewPageFragmentAdapter;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.ui.PagerSlidingTabStrip;

import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by aaron on 15/11/27.
 */
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(id = R.id.id_pagerSlidingTabStrip)
    PagerSlidingTabStrip mTabStrip;

    @InjectView(id = R.id.id_viewpager)
    ViewPager mViewPager;

    @InjectView(id = R.id.main_content)
    SwipeRefreshLayout swipeRefreshLayout;

    ViewPageFragmentAdapter mTabsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Injector.injectInto(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        initViews();
        return view;
    }

    public void initViews() {
        mTabsAdapter = new ViewPageFragmentAdapter(getFragmentManager(), mViewPager);

        mViewPager.setAdapter(mTabsAdapter);
        mTabStrip.setViewPager(mViewPager);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        onResume();
    }
}
