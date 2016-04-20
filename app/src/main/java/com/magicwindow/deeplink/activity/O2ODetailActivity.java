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
import cn.salesuite.saf.utils.ToastUtils;

public class O2ODetailActivity extends BaseAppCompatActivity {

    @InjectView
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o2o_detail);
        initToolBar();
    }

    private void initToolBar() {

        toolbar.setTitle(R.string.o2o_detail);
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
            if (MarketingHelper.currentMarketing(this).isActive(Config.MW_O2O_SHARE)) {
                MarketingHelper.currentMarketing(this).click(this, Config.MW_O2O_SHARE);
            } else {
                ToastUtils.showShort(this, R.string.share_closed);
            }
        }

        return super.onOptionsItemSelected(item);

    }
}
