package com.magicwindow.deeplink.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.ImageAdapter;
import com.magicwindow.deeplink.adapter.TourListAdapter;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.domain.TravelList;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.magicwindow.deeplink.ui.ListViewForScrollView;
import com.zxinsight.TrackAgent;

import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import me.relex.circleindicator.CircleIndicator;

/**
 * @author Tony Shen
 * @date 15/11/25
 */
public class TourFragment extends BaseFragment {

    @InjectView(id = R.id.viewpager)
    ViewPager viewPager;

    @InjectView(id = R.id.indicator)
    CircleIndicator indicator;

    @InjectView(id = R.id.home_list)
    ListViewForScrollView homeList;

    private MWBroadCastReceiver receiver;
    private TourListAdapter adapter;
    private int guideResourceId = R.drawable.guide_tour;// 新手引导页图片资源id
    private FrameLayout guideFrameLayout;
    private AppPrefs appPrefs;

    @Override
    public void onStart() {
        super.onStart();
        appPrefs = AppPrefs.get(mContext);
        if (!appPrefs.getGuideTour()) {
            addGuideImage();// 添加新手引导图片
        }
    }

    private void addGuideImage() {
        View view = mContext.getWindow().getDecorView().findViewById(R.id.root);//
        // 查找通过setContentView上的根布局
        if (view == null)
            return;

        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            guideFrameLayout = (FrameLayout) viewParent;
        }

        final ImageView guideImage = new ImageView(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        guideImage.setLayoutParams(params);
        guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
        guideImage.setImageResource(guideResourceId);
        guideImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideFrameLayout.removeView(guideImage);
                appPrefs.setGuideTour(true);
            }
        });
        guideFrameLayout.addView(guideImage);// 添加引导图片
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            //@mw 页面跳转
            TrackAgent.currentEvent().onPageStart("旅游页");
        } else {
            //@mw 页面跳转
            TrackAgent.currentEvent().onPageEnd("旅游页");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tour, container,
                false);
        Injector.injectInto(this, view);

        initViews();
        initData();

        return view;
    }

    private void initViews() {
        TravelList travelList = AppPrefs.get(mContext).getTravelList();

        viewPager.setAdapter(new ImageAdapter(0, travelList.headList));
        indicator.setViewPager(viewPager);

        adapter = new TourListAdapter(mContext, travelList.contentList);
        homeList.setAdapter(adapter);
    }

    private void initData() {
        receiver = new MWBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.magicwindow.marketing.update.MW_MESSAGE");
        mContext.registerReceiver(receiver, filter);
    }


    //@mw 魔窗位绑定
//    private void bindMW() {
//        banner.getView(0).bindEvent(Config.MWS[0]);
//        banner.getView(0).setScaleType(ImageView.ScaleType.FIT_XY);
//        banner.getView(1).bindEvent(Config.MWS[1]);
//        banner.getView(1).setScaleType(ImageView.ScaleType.FIT_XY);
//        banner.getView(2).bindEvent(Config.MWS[2]);
//        banner.getView(2).setScaleType(ImageView.ScaleType.FIT_XY);
//        banner.getView(3).bindEvent(Config.MWS[3]);
//        banner.getView(3).setScaleType(ImageView.ScaleType.FIT_XY);
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public class MWBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.e("aaron", "action = " + action);
            if (action.equals("com.magicwindow.marketing.update.MW_MESSAGE")) {
                //todo: 你的代码 这个是活动webview onResume消息
                adapter.notifyDataSetChanged();
            }
        }
    }
}
