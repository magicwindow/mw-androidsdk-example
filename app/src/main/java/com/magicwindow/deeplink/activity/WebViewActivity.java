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

/**
 * @author Aaron.Liu
 */
public class WebViewActivity extends BaseAppCompatActivity {
    public final static String WEB_URL = "web_url";
    private static final String TAG = "WebViewActivity ";
    private WebView webView;
    private Toolbar toolbar;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(com.magicwindow.deeplink.R.id.webview);
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

        toolbar = (Toolbar) findViewById(com.magicwindow.deeplink.R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        webView.getSettings().setJavaScriptEnabled(true);
        load();
    }

    private void load() {
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
}