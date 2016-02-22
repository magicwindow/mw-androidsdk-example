package com.magicwindow.deeplink.activity;

import android.os.Bundle;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.zxinsight.MarketingHelper;

import cn.salesuite.saf.inject.annotation.OnClick;

/**
 * @author aaron
 * @date 16/01/14
 */
public class TourDetailActivity extends BaseAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
    }

    @OnClick(id= R.id.tour_detail_button)
    void clickButton() {
        MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[91]);
    }
}
