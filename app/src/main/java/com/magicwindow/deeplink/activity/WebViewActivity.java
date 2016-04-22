/**
 *
 */
package com.magicwindow.deeplink.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.zxinsight.TrackAgent;

import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * @author Aaron.Liu
 */
public class WebViewActivity extends BaseAppCompatActivity {

    @InjectView(id = R.id.webview)
    WebView webView;

    @InjectView
    Toolbar toolbar;

    public final static String WEB_URL = "web_url";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        initViews();
        initData();
    }

    private void initViews() {

        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         if (url.startsWith("unsafe:")) {
                                             url = url.substring(7);
                                         }
                                         view.loadUrl(url);
                                         return super.shouldOverrideUrlLoading(view, url);
                                     }
                                 }
        );
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(false);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(WEB_URL);
        webView.loadUrl(url);
        toolbar.setTitle(webView.getTitle());
    }

    @Override
    public void onPause() {
        TrackAgent.currentEvent().onPageEnd("WebViewActivity");
        super.onPause();
    }

    @Override
    public void onResume() {
        TrackAgent.currentEvent().onPageStart("WebViewActivity");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }

    }
}