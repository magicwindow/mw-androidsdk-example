package com.magicwindow.deeplink.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;

/**
 * 旅游的详情页面,绑定了uber的mLink服务.</br>
 * Config.MWS[91]:大众点评的魔窗位,跳转到大众点评三亚的美食服务 </br>
 * Config.MWS[100]:携程的魔窗位,跳转到携程的酒店团购服务 </br>
 * Config.MWS[101]:携程的魔窗位,跳转到携程的特价机票服务 </br>
 * @author aaron
 * @date 16/01/14
 */
public class TourDetailActivity extends BaseAppCompatActivity {

    @InjectView(id = R.id.tour_detail_uber)
    MWImageView uber;

    @InjectView(id = R.id.tour_detail_button)
    MWImageView button;

    @InjectView(id = R.id.tour_detail_button1)
    MWImageView button1;

    @InjectView(id = R.id.tour_detail_button2)
    MWImageView button2;

    @InjectView
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        initViews();
    }

    private void initViews() {

        toolbar.setTitle(R.string.tour_detail_name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initMW();
    }

    private void initMW() {
        if (MarketingHelper.currentMarketing(this).isActive(Config.MWS[99])) {
            uber.bindEvent(Config.MWS[99]);
            uber.setVisibility(View.VISIBLE);
        } else {
            uber.setVisibility(View.GONE);
        }

        if (MarketingHelper.currentMarketing(this).isActive(Config.MWS[91])) {
            button.bindEvent(Config.MWS[91]);
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }

        if (MarketingHelper.currentMarketing(this).isActive(Config.MWS[100])) {
            button1.bindEvent(Config.MWS[100]);
            button1.setVisibility(View.VISIBLE);
        } else {
            button1.setVisibility(View.GONE);
        }

        if (MarketingHelper.currentMarketing(this).isActive(Config.MWS[101])) {
            button2.bindEvent(Config.MWS[101]);
            button2.setVisibility(View.VISIBLE);
        } else {
            button2.setVisibility(View.GONE);
        }
    }

    @OnClick(id = R.id.tour_detail_button)
    void clickButton() {
        MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[91]);
    }

    @OnClick(id = R.id.tour_detail_button1)
    void clickButton1() {
        MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[100]);
    }

    @OnClick(id = R.id.tour_detail_button2)
    void clickButton2() {
        MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[101]);
    }

}
