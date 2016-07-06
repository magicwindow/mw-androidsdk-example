package com.zxinsight.magicwindow;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.zxinsight.MLink;
import com.zxinsight.MagicWindowSDK;
import com.zxinsight.MarketingHelper;
import com.zxinsight.magicwindow.activity.DetailActivity;
import com.zxinsight.magicwindow.activity.HomeActivity;
import com.zxinsight.magicwindow.config.Config;
import com.zxinsight.mlink.MLinkCallback;
import com.zxinsight.mlink.MLinkIntentBuilder;

import java.util.Map;

/**
 * Created by Tony Shen on 16/1/14.
 */
public class UrlDispatcher {

    public static void register(Context context) {
        MLink mLink = MLink.getInstance(context);

        mLink.register("mw", new MLinkCallback() {

            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MarketingHelper.currentMarketing(context).click(context, Config.MW_DETAIL);
            }
        });


    }

    public static void registerWithMLinkCallback(Context context) {
        MLink mLink = MagicWindowSDK.getMLink();
        mLink.register("product", new MLinkCallback() {
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {


                MLinkIntentBuilder.buildIntent(paramMap, context, DetailActivity.class);

            }
        });
        mLink.register("detail", new MLinkCallback() {
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, DetailActivity.class);
            }
        });
        mLink.register("goodsDetailKey", new MLinkCallback() {
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                MLinkIntentBuilder.buildIntent(paramMap, context, DetailActivity.class);
            }
        });

        mLink.register("mw", new MLinkCallback() {

            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {
                if (!TextUtils.isEmpty(paramMap.get("key")) && MarketingHelper.currentMarketing(context).isActive(paramMap.get("key"))) {
                    MarketingHelper.currentMarketing(context).click(context, paramMap.get("key"));
                } else {
                    MLinkIntentBuilder.buildIntent(paramMap, context, HomeActivity.class);
                }
            }
        });

        mLink.registerDefault(new MLinkCallback() {

            @Override
            public void execute(Map<String, String> paramMap, Uri uri, Context context) {

                //
                MLinkIntentBuilder.buildIntent(paramMap, context, HomeActivity.class);

            }
        });
    }
}
