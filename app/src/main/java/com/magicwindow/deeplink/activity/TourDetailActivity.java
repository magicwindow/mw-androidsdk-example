package com.magicwindow.deeplink.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.TravelDetail;
import com.magicwindow.deeplink.prefs.AppPrefs;
import cn.magicwindow.MWImageView;
import cn.magicwindow.MarketingHelper;

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

    @InjectView(id = R.id.tour_detail_banner)
    MWImageView tour_detail_banner;

    @InjectView(id = R.id.map_img)
    ImageView map_img;

    @InjectView(id = R.id.tour_detail_car)
    TextView tour_detail_car;

    @InjectView(id = R.id.food01)
    ImageView food01;

    @InjectView(id = R.id.food02)
    ImageView food02;

    @InjectView(id = R.id.food03)
    ImageView food03;

    @InjectView(id = R.id.more_food)
    MWImageView more_food;

    @InjectView(id = R.id.hotel_img)
    ImageView hotel_img;

    @InjectView(id = R.id.order)
    MWImageView order;

    @InjectView(id = R.id.hotel_layout1)
    RelativeLayout hotel_layout1;

    @InjectView(id = R.id.hotel_layout2)
    RelativeLayout hotel_layout2;

    @InjectView(id = R.id.travel_img01)
    ImageView travel_img01;

    @InjectView(id = R.id.travel_img02)
    ImageView travel_img02;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);
        initViews();
        displayImage();
        bindMW();
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

    private void displayImage() {
        TravelDetail detail = AppPrefs.get(this).getTravelDetail();
        app.imageLoader.displayImage(detail.banner, tour_detail_banner);
        app.imageLoader.displayImage(detail.map, map_img);
        app.imageLoader.displayImage(detail.stay, hotel_img);
        app.imageLoader.displayImage(detail.travel.get(0), travel_img01);
        app.imageLoader.displayImage(detail.travel.get(1), travel_img02);
        app.imageLoader.displayImage(detail.food.get(0), food01);
        app.imageLoader.displayImage(detail.food.get(1), food02);
        app.imageLoader.displayImage(detail.food.get(2), food03);


    }

    private void bindMW(){

        //绑定旅游-detail-Uber
//        MaterialRippleLayout.on(tour_detail_car).create();
        tour_detail_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(TourDetailActivity.this).click(TourDetailActivity.this, Config.MW_TOUR_DETAIL_UBER);
            }
        });

        //绑定旅游-detail01
        more_food.bindEvent(this, Config.MW_TOUR_DETAIL_DIANPING);
        //绑定旅游-detail02
        order.bindEvent(this, Config.MW_TOUR_DETAIL_CTRIP_HOTEL);
        //绑定旅游-detail03
        hotel_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(TourDetailActivity.this).click(TourDetailActivity.this, Config.MW_TOUR_DETAIL_CTRIP_AIRCRAFT);
            }
        });
        //绑定旅游-detail03
        hotel_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketingHelper.currentMarketing(TourDetailActivity.this).click(TourDetailActivity.this, Config.MW_TOUR_DETAIL_CTRIP_AIRCRAFT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (MarketingHelper.currentMarketing(this).isActive(Config.MW_TOUR_SHARE)) {
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
            if (MarketingHelper.currentMarketing(this).isActive(Config.MW_TOUR_SHARE)) {
                MarketingHelper.currentMarketing(this).click(this, Config.MW_TOUR_SHARE);
            } else {
                toast(R.string.share_closed);
            }
        }

        return super.onOptionsItemSelected(item);

    }

}
