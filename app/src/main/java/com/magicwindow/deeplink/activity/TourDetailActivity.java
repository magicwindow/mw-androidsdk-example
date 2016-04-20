package com.magicwindow.deeplink.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.zxinsight.MarketingHelper;

import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * 旅游的详情页面,绑定了uber的mLink服务.</br>
 * Config.MWS[91]:大众点评的魔窗位,跳转到大众点评三亚的美食服务 </br>
 * Config.MWS[100]:携程的魔窗位,跳转到携程的酒店团购服务 </br>
 * Config.MWS[101]:携程的魔窗位,跳转到携程的特价机票服务 </br>
 *
 * @author aaron
 * @date 16/01/14
 */
public class TourDetailActivity extends BaseAppCompatActivity {

    @InjectView
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        initViews();
    }

    private void initViews() {

        toolbar.setTitle(R.string.tour_detail_title);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_share) {
            if (MarketingHelper.currentMarketing(this).isActive(Config.MW_NEWS_SHARE)) {
                MarketingHelper.currentMarketing(this).click(this, Config.MW_NEWS_SHARE);
            } else {
                toast(R.string.share_closed);
            }
        }

        return super.onOptionsItemSelected(item);

    }

}
