package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.O2ODetail;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.zxinsight.MarketingHelper;
import com.zxinsight.mlink.annotation.MLinkRouter;

import cn.salesuite.saf.inject.annotation.InjectView;

@MLinkRouter(keys = {"second","O2Odetail"})
public class O2ODetailActivity extends BaseAppCompatActivity {

    @InjectView
    Toolbar toolbar;

    @InjectView(id = R.id.header)
    ImageView o2oHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o2o_detail);
        initToolBar();
        displayImage();
        Intent intent = getIntent();
//        if(intent!=null){
//            String name = intent.getStringExtra("name");
//            Toast.makeText(this,"name = "+name,Toast.LENGTH_LONG).show();
//
//        }
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

    private void displayImage() {
        O2ODetail detail = AppPrefs.get(this).getO2ODetail();
        app.imageLoader.displayImage(detail.detail,o2oHeader);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (MarketingHelper.currentMarketing(this).isActive(Config.MW_O2O_SHARE)) {
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
            if (MarketingHelper.currentMarketing(this).isActive(Config.MW_O2O_SHARE)) {
                MarketingHelper.currentMarketing(this).click(this, Config.MW_O2O_SHARE);
            } else {
                toast(R.string.share_closed);
            }
        }

        return super.onOptionsItemSelected(item);

    }
}
