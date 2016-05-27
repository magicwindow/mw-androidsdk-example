package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.ImageAdapter;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.ShopDetail;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.zxinsight.MarketingHelper;
import com.zxinsight.TrackAgent;

import java.util.HashMap;

import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;
import cn.salesuite.saf.utils.ToastUtils;
import me.relex.circleindicator.CircleIndicator;

public class ShopDetailActivity extends BaseAppCompatActivity {

    @InjectView
    Toolbar toolbar;

    @InjectView(id = R.id.viewpager)
    ViewPager viewPager;

    @InjectView(id = R.id.indicator)
    CircleIndicator indicator;

    @InjectView(id = R.id.shop_detail_img)
    ImageView shopDetailImg;

    private ShopDetail shopDetail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initToolBar();
    }

    private void initToolBar() {

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!
        setViewPager();
    }
    private void setViewPager() {
        shopDetail = AppPrefs.get(this).getShopDetail();
        app.imageLoader.displayImage(shopDetail.content,shopDetailImg);

        viewPager.setAdapter(new ImageAdapter(-1, shopDetail.headList, R.drawable.default_640_640));
        indicator.setViewPager(viewPager);
    }

    @OnClick(id = R.id.click_to_buy)
    public void clickBuy() {
        HashMap map = new HashMap();
        map.put("goods", "婴儿奶嘴");
        map.put("price", "14.49");
        TrackAgent.currentEvent().customEvent(Config.CUSTOM_ADD_TO_SHOP_CART, map);
        startActivity(new Intent(mContext, ShopCartActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(MarketingHelper.currentMarketing(this).isActive(Config.MW_SHOP_SHARE)){
            getMenuInflater().inflate(R.menu.menu_shop_detail, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_share) {
            if (MarketingHelper.currentMarketing(this).isActive(Config.MW_SHOP_SHARE)) {
                MarketingHelper.currentMarketing(this).click(this, Config.MW_SHOP_SHARE);
            } else {
                ToastUtils.showShort(this, R.string.share_closed);
            }
        }

        return super.onOptionsItemSelected(item);

    }
}
