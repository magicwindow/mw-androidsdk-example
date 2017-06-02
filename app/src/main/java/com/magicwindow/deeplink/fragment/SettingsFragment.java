package com.magicwindow.deeplink.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.activity.LearnActivity;
import com.magicwindow.deeplink.activity.LoginActivity;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.User;
import com.magicwindow.deeplink.task.GetuiPushTask;
import cn.magicwindow.MarketingHelper;
import cn.magicwindow.TrackAgent;

import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.AsyncTaskExecutor;

/**
 * 设置页面
 * 可点击登陆跳转到登陆界面登陆，已登陆状态可退出
 * 可点击查看引导页
 */
public class SettingsFragment extends BaseFragment {

    @InjectView
    private PullToZoomScrollViewEx scrollView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            L.e("SettingsFragment visible");
            TrackAgent.currentEvent().onPageStart("“我”页");

        } else {
            L.e("SettingsFragment invisible");
            TrackAgent.currentEvent().onPageEnd("“我”页");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Injector.injectInto(this, view);

        return view;
    }

    @Override
    public void initView() {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.settings_head_view, null, false);
        View zoomView = LayoutInflater.from(mContext).inflate(R.layout.settings_zoom_view, null, false);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.settings_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        final TextView login = (TextView) scrollView.getPullRootView().findViewById(R.id.login);
        login.setText(User.currentUser().isLoggedIn()?R.string.logout:R.string.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.currentUser().isLoggedIn()) {
                    User.currentUser().logout();
                    login.setText(R.string.login);
                    toast(R.string.user_have_quit);
                } else {
                    Intent i = new Intent(mContext, LoginActivity.class);
                    startActivity(i);
                }
            }
        });

        scrollView.getPullRootView().findViewById(R.id.custom_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[92]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.user_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[93]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.grade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[94]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.point).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[95]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.to_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[96]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MarketingHelper.currGetuiPushTaskentMarketing(mContext).click(mContext, Config.MWS[96]);
                AsyncTaskExecutor.executeAsyncTask(new GetuiPushTask());
            }
        });
        scrollView.getPullRootView().findViewById(R.id.my_achievement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[97]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.my_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[98]);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.learning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, LearnActivity.class);
                i.putExtra(LearnActivity.TYPE, LearnActivity.FROM_SETTING);
                startActivity(i);
            }
        });
    }

}
