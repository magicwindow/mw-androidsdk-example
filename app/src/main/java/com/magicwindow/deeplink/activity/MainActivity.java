package com.magicwindow.deeplink.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.MenuLeftAdapter;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.app.BaseAsyncTask;
import com.magicwindow.deeplink.citySelect.CitiesActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.DownloadResponse;
import com.magicwindow.deeplink.domain.event.UpdateAppEvent;
import com.magicwindow.deeplink.download.UpdateDownloadTaskListener;
import com.magicwindow.deeplink.menu.MenuManager;
import com.magicwindow.deeplink.ui.dialog.MWDialog;
import com.magicwindow.deeplink.utils.DoubleClickExitUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.salesuite.mlogcat.activity.LogcatActivity;
import cn.salesuite.saf.download.DownloadManager;
import cn.salesuite.saf.eventbus.Subscribe;
import cn.salesuite.saf.http.rest.HttpResponseHandler;
import cn.salesuite.saf.http.rest.RestClient;
import cn.salesuite.saf.http.rest.RestException;
import cn.salesuite.saf.http.rest.RestUtil;
import cn.salesuite.saf.http.rest.UrlBuilder;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.AsyncTaskExecutor;
import cn.salesuite.saf.utils.Preconditions;
import cn.salesuite.saf.utils.SAFUtils;
import cn.salesuite.saf.utils.StringUtils;
import cn.salesuite.saf.view.LightDialog;

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

    private ProgressDialog pBar;
    private LightDialog dialog;

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

        CheckUpdateTask checkUpdateTask = new CheckUpdateTask();
        AsyncTaskExecutor.executeAsyncTask(checkUpdateTask);
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
            if (menu != null) {
                menu.setTitle(result);
            }
        }
    }

    class CheckUpdateTask extends BaseAsyncTask<String, String[], Integer> {

        private DownloadResponse response;

        @Override
        protected Integer onExecute(String... arg0) {
            String urlString;

            try {
                UrlBuilder builder = new UrlBuilder("http://demoapp.test.magicwindow.cn/v1/demoapp/checkUpdate");
                urlString = builder.buildUrl();

                JSONObject json = new JSONObject();
                json.put("os","0"); // 0表示android,1表示iOS
                json.put("currentVersion",app.version);

                RestClient.post(urlString,json, new HttpResponseHandler() {

                    @Override
                    public void onSuccess(String content, Map<String, List<String>> header) {
                        try {
                            response = RestUtil.parseAs(DownloadResponse.class, content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(RestException arg0) {
                    }

                });

                if (response == null) {
                    throw new IOException();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return Config.RESULT_IOERROR;
            } catch (Exception e) {
                return Config.RESULT_IOERROR;
            }

            return Config.RESULT_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result == Config.RESULT_SUCCESS) {
                checkUpdate(response);
            }
        }

    }

    public void checkUpdate(final DownloadResponse response) {

        // 只有升级状态为true并且强制升级状态为true，才强制升级， 不需要弹出选择对话框
        if (response.result.upgrade && response.result.forceUpgrade) {
            if (SAFUtils.isWiFiActive(mContext)) { // wifi情况下，弹出升级提示
                doUpdateEvent(response);
            }
        } else if (response.result.upgrade) {
            if (dialog == null) {
                dialog = LightDialog.create(mContext, "软件更新",
                        String.valueOf(Html.fromHtml(response.result.desc)));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doUpdateEvent(response);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                if (!MainActivity.this.isFinishing() && !dialog.isShowing()) {
                    dialog.show();
                }
            }
        }
    }

    /**
     * 触发app升级下载监听者事件
     * @param response
     */
    void doUpdateEvent(DownloadResponse response) {

        UpdateAppEvent event = new UpdateAppEvent();
        event.url = response.result.newVersionUrl;
        eventBus.post(event);
    }

    /**
     * 升级app的事件
     *
     * @param event
     */
    @Subscribe
    public void onUpdateAppEvent(UpdateAppEvent event) {

        if (StringUtils.isNotBlank(event.url) && SAFUtils.checkNetworkStatus(mContext)) {
            String url = event.url;
            String path = Environment.getExternalStorageDirectory().getPath() + Config.DIR + "/";
            String fileName = "mwdemo" + System.currentTimeMillis()+ ".apk";
            final String apkPathUrl = path + fileName;
            DownloadManager.getInstance(app).startDownload(url, path, fileName,
                    new UpdateDownloadTaskListener(pBar, mContext, apkPathUrl));
        }
    }
}