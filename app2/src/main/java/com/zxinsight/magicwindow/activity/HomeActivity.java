package com.zxinsight.magicwindow.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.zxinsight.MagicWindowSDK;
import com.zxinsight.magicwindow.R;
import com.zxinsight.magicwindow.UrlDispatcher;
import com.zxinsight.magicwindow.adapter.HomeListAdapter;
import com.zxinsight.magicwindow.adapter.ImageAdapter;
import com.zxinsight.magicwindow.domain.User;
import com.zxinsight.magicwindow.view.ListViewForScrollView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends BaseAppCompatActivity {

    ViewPager viewPager;

    CircleIndicator indicator;

    ListViewForScrollView homeList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_home);
        HomeListAdapter adapter = new HomeListAdapter(mContext);

        homeList = (ListViewForScrollView) findViewById(R.id.home_list);
        homeList.setAdapter(adapter);
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(HomeActivity.this, DetailActivity.class));
            }
        });

        initViewPager();
        initToolBar();
    }



    private void initViewPager() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.home_banner001);
        list.add(R.drawable.home_banner002);
        list.add(R.drawable.home_banner003);
        list.add(R.drawable.home_banner004);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        viewPager.setAdapter(new ImageAdapter(0, list));
        indicator.setViewPager(viewPager);
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onPause() {
//        TrackAgent.currentEvent().onPageEnd("主页");

        super.onPause();
    }

    @Override
    public void onResume() {
//        TrackAgent.currentEvent().onPageStart("主页");
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.right_menu) {
            User.currentUser().logout();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



}
