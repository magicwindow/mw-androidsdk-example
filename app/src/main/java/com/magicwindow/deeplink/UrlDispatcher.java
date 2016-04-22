package com.magicwindow.deeplink;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.magicwindow.deeplink.activity.NewsDetailActivity;
import com.magicwindow.deeplink.activity.MainActivity;
import com.magicwindow.deeplink.activity.O2ODetailActivity;
import com.magicwindow.deeplink.activity.ShopDetailActivity;
import com.magicwindow.deeplink.activity.VideoDetailActivity;
import com.zxinsight.MLink;
import com.zxinsight.MarketingHelper;
import com.zxinsight.mlink.MLinkCallback;
import com.zxinsight.mlink.MLinkIntentBuilder;
import com.zxinsight.mlink.MLinkListener;

import java.util.Map;

/**
 * @author Aaron
 * @date 16/1/26
 */
public class UrlDispatcher {

    public static void register(Context context) {
        MLink mLink = MLink.getInstance(context);

        //// TODO: @mw  注册默认跳转（必加）
        mLink.registerDefault(new MLinkCallback() {
            @Override
            public void execute(Map<String, String> map, Uri uri, Context context) {
                Log.e("aaron", "default uri = " + uri);
                MLinkIntentBuilder.buildIntent(map, context, MainActivity.class);
            }
        });

        //@mw 注册“跳转到具体的魔窗活动页”,"campaignKey"为魔窗后台相应的mLink唯一标识
        mLink.register("campaignKey", new MLinkCallback() {

            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {

                Log.e("aaron", "uri = " + uri);
                //如果活动关闭则跳转到首页
                if (!MarketingHelper.currentMarketing(context).isActive(paramMap.get("key"))) {
                    MLinkIntentBuilder.buildIntent(paramMap, context, MainActivity.class);
                } else {
                    MarketingHelper.currentMarketing(context).click(context, paramMap.get("key"));
                }
            }
        });


        //@mw 注册"跳转到 电商、O2O、资讯、图库、个人中心",
        mLink.register("viewKey", new MLinkCallback() {

            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {

                MLinkIntentBuilder.buildIntent(paramMap, context, MainActivity.class);

            }
        });

        mLink.register("VideoDetail", VideoDetailActivity.class, new MLinkListener() {
            @Override
            public Map<String, String> getExtraParams(Map<String, String> map) {
                Log.e("aaron", "VideoDetailActivity map = " + map);
                return map;
            }
        });

        mLink.register("NewsDetail", NewsDetailActivity.class, new MLinkListener() {
            @Override
            public Map<String, String> getExtraParams(Map<String, String> map) {
                Log.e("aaron", "NewsDetailActivity map = " + map);
                return map;
            }
        });

        mLink.register("O2Odetail", O2ODetailActivity.class, new MLinkListener() {
            @Override
            public Map<String, String> getExtraParams(Map<String, String> map) {
                Log.e("aaron", "O2ODetailActivity map = " + map);
                return map;
            }
        });
        mLink.register("dianshangDetail", ShopDetailActivity.class, new MLinkListener() {
            @Override
            public Map<String, String> getExtraParams(Map<String, String> map) {
                Log.e("aaron", "ShopDetailActivity map = " + map);
                return map;
            }
        });


    }

}
