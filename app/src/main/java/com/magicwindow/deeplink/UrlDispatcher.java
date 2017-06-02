package com.magicwindow.deeplink;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.magicwindow.deeplink.activity.MainActivity;
import com.magicwindow.deeplink.activity.NewsDetailActivity;
import com.magicwindow.deeplink.activity.O2ODetailActivity;
import com.magicwindow.deeplink.activity.ShopDetailActivity;
import com.magicwindow.deeplink.activity.VideoDetailActivity;
import cn.magicwindow.MLink;
import cn.magicwindow.MarketingHelper;
import cn.magicwindow.mlink.MLinkCallback;
import cn.magicwindow.mlink.MLinkIntentBuilder;

import java.util.Map;

/**
 * @author Aaron
 * @date 16/1/26
 */
public class UrlDispatcher {

    public static void register(Context ctx) {
        MLink mLink = MLink.getInstance(ctx);

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
            public void execute(final Map<String, String> paramMap, final Uri uri, final Context context) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("aaron", "uri = " + uri);
                        //如果活动关闭则跳转到首页
                        if (!MarketingHelper.currentMarketing(context).isActive(paramMap.get("key_android"))) {
                            Log.e("aaron", "key1 = " + paramMap.get("key_android"));
                            MLinkIntentBuilder.buildIntent(paramMap, context, MainActivity.class);
                        } else {
                            Log.e("aaron", "key2 = " + paramMap.get("key"));
                            MarketingHelper.currentMarketing(context).click(context, paramMap.get("key_android"));
                        }
                    }
                }, 500);

            }
        });


        //@mw 注册"跳转到 电商、O2O、资讯、图库、个人中心",
        mLink.register("viewKey", new MLinkCallback() {

            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {

                MLinkIntentBuilder.buildIntent(paramMap, context, MainActivity.class);

            }
        });

        mLink.register("VideoDetail", new MLinkCallback() {
            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, VideoDetailActivity.class);

            }
        });

        mLink.register("NewsDetail", new MLinkCallback() {
            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, NewsDetailActivity.class);

            }
        });

        mLink.register("O2Odetail", new MLinkCallback() {
            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, O2ODetailActivity.class);

            }
        });
        mLink.register("second", new MLinkCallback() {
            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, O2ODetailActivity.class);

            }
        });
//        mLink.register("second", new MLinkCallback() {
//            @Override
//            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
//                MLinkIntentBuilder.buildIntent(paramMap, context, O2ODetailActivity.class);
//
//            }
//        });
        mLink.register("dianshangDetail", new MLinkCallback() {
            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, ShopDetailActivity.class);

            }
        });


    }

}
