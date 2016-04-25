package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.MenuLeftAdapter;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.citySelect.CitiesActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.menu.MenuManager;
import com.magicwindow.deeplink.ui.dialog.MWDialog;
import com.magicwindow.deeplink.utils.DoubleClickExitUtils;

import cn.salesuite.mlogcat.activity.LogcatActivity;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.Preconditions;

public class MainActivity extends BaseAppCompatActivity {


    @InjectView(id = R.id.menu_profile)
    RelativeLayout menuProfile;

    @InjectView(id = R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView
    Toolbar toolbar;

    @InjectView
    TextView appVersion;

    @InjectView(id = R.id.menu_list)
    ListView menuLeft;

    private MenuManager menuManager;
    private DoubleClickExitUtils doubleClickExitHelper;
    private int mPosition = 0;
    public static String PAGE = "page";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            Intent intent = getIntent();
            String pageStr = intent.getStringExtra(PAGE);
            if (Preconditions.isNotBlank(pageStr)) {
                switch (pageStr) {
                    case "0":
                    case "lvyou":
                    case "home":
                        mPosition = 0;
                        break;
                    case "1":
                    case "o2o":
                    case "O2O":
                        mPosition = 1;
                        break;
                    case "2":
                    case "tuku":
                    case "galleries":
                        mPosition = 2;
                        break;
                    case "3":
                    case "dianshang":
                    case "ebusiness":
                        mPosition = 3;
                        break;
                    case "4":
                    case "zixun":
                    case "news":
                        mPosition = 4;
                        break;
                    case "99":
                        mPosition = 99;
                        break;
                }

                L.i("mPosition = " + mPosition);
            }
        }
        initViews();
        initData();
    }

    @OnClick(id = R.id.appVersion)
    void clickAppVersion() {
        Intent i = new Intent(this, LogcatActivity.class);
        startActivity(i);
    }

    private void initViews() {

        setSupportActionBar(toolbar);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        appVersion.setText(String.format(getString(com.magicwindow.deeplink.R.string.menu_app_version), app.version));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, com.magicwindow.deeplink.R.string.drawer_open, com.magicwindow.deeplink.R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                menuManager.showByPosition(mPosition);
                toolbar.setTitle(menuManager.getCurType().getTitleRes());
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                toolbar.setTitle(com.magicwindow.deeplink.R.string.menu);
                invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void initData() {

        doubleClickExitHelper = new DoubleClickExitUtils(this);

        menuManager = new MenuManager(getSupportFragmentManager());
        menuManager.showByPosition(mPosition);

        toolbar.setTitle(menuManager.getCurType().getTitleRes());

        menuLeft.setAdapter(new MenuLeftAdapter(this));

        menuLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mPosition = position;
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = MenuManager.MenuType.SETTINGS.position;
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_city) {
            startActivityForResult(new Intent(this, CitiesActivity.class), 0);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doubleClickExitHelper.onKeyDown(keyCode, event);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            ActionMenuItemView menu = (ActionMenuItemView) findViewById(R.id.action_city);
            menu.setTitle(result);
        }
    }
}