package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import cn.magicwindow.TrackAgent;

import java.util.HashMap;

import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;

public class ShopPayActivity extends BaseAppCompatActivity {

    @InjectView(id = R.id.weixin_selected)
    ImageView weixinSelected;

    @InjectView(id = R.id.zhifubao_selected)
    ImageView zhifubaoSelected;

    @InjectView
    Toolbar toolbar;

    private final int STATUS_WEIXIN = 0;
    private final int STATUS_ZHIFUBAO = 1;
    private int STATUS = STATUS_WEIXIN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_pay);
        initViews();
        initData();
    }

    private void initData() {
        if (STATUS == STATUS_WEIXIN) {
            weixinSelected.setImageResource(R.drawable.pay_select_pressed);
            zhifubaoSelected.setImageResource(R.drawable.pay_select);
        } else if (STATUS == STATUS_ZHIFUBAO) {
            zhifubaoSelected.setImageResource(R.drawable.pay_select_pressed);
            weixinSelected.setImageResource(R.drawable.pay_select);
        }
    }

    private void initViews() {
        toolbar.setTitle(R.string.shop_pay);
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

    @OnClick(id = R.id.weixin_layout)
    public void weixinPay() {
        STATUS = STATUS_WEIXIN;
        weixinSelected.setImageResource(R.drawable.pay_select_pressed);
        zhifubaoSelected.setImageResource(R.drawable.pay_select);
    }


    @OnClick(id = R.id.zhifubao_layout)
    public void zhifubaoPay() {
        STATUS = STATUS_ZHIFUBAO;
        zhifubaoSelected.setImageResource(R.drawable.pay_select_pressed);
        weixinSelected.setImageResource(R.drawable.pay_select);
    }

    @OnClick(id = R.id.click_to_buy)
    public void clickBuy() {
        if (STATUS == 0) {
            HashMap map = new HashMap();
            map.put("pay_channel","WeiXin");
            TrackAgent.currentEvent().customEvent(Config.CUSTOM_ADD_TO_SHOP_CART,map);
            startActivity(new Intent(mContext, ShopDoneActivity.class));
        } else if (STATUS == 1) {
            HashMap map = new HashMap();
            map.put("pay_channel","ZhiFuBao");
            TrackAgent.currentEvent().customEvent(Config.CUSTOM_ADD_TO_SHOP_CART,map);
            startActivity(new Intent(mContext, ShopDoneActivity.class));
        } else {
            toast(R.string.select_pay);
        }
    }
}
