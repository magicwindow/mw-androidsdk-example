package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.zxinsight.TrackAgent;

import java.util.HashMap;

import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;

public class ShopDetailActivity extends BaseAppCompatActivity {

    @InjectView
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initViews();
    }

    private void initViews() {

        toolbar.setTitle(R.string.shop_detail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @OnClick(id = R.id.click_to_buy)
    public void clickBuy() {
        HashMap map = new HashMap();
        map.put("goods","婴儿奶嘴");
        map.put("price","14.49");
        TrackAgent.currentEvent().customEvent(Config.CUSTOM_ADD_TO_SHOP_CART,map);
        startActivity(new Intent(mContext, ShopCartActivity.class));
    }
}
