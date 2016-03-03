package com.magicwindow.deeplink.utils;

import android.content.Context;

import com.magicwindow.deeplink.app.MWApplication;
import com.magicwindow.deeplink.prefs.AppPrefs;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import cn.salesuite.saf.async.AsyncTask;
import cn.salesuite.saf.http.rest.HttpResponseHandler;
import cn.salesuite.saf.http.rest.RestClient;
import cn.salesuite.saf.http.rest.RestException;
import cn.salesuite.saf.http.rest.UrlBuilder;

/**
 * @author aaron
 * @date 16/3/3
 */
public class NetTask extends AsyncTask {
    private static final String HOST = "http://121.40.195.177/list/";
    private final List<String> mList;
    private final Context mContext;
    private final AppPrefs appPrefs;

    public NetTask(Context context, List<String> list) {
        mContext = context;
        mList = list;
        appPrefs = AppPrefs.get(mContext);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (mList != null && mList.size() > 0) {
            for (String path : mList) {
                initAssetsJson(path);
                initJsonFromNet(path);
            }
        }
        return null;
    }

    private void initJsonFromNet(final String path) {
        String urlString;

        try {
            UrlBuilder builder = new UrlBuilder(HOST + path);

            urlString = builder.buildUrl();

            RestClient.get(urlString, new HttpResponseHandler() {


                @Override
                public void onSuccess(String s, Map<String, List<String>> map) {
                    appPrefs.saveJson(path, s);
                }

                @Override
                public void onFail(RestException arg0) {

                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initAssetsJson(String path) {

        if (appPrefs.getLastVersion() != null && appPrefs.getLastVersion().equals(MWApplication.getInstance().version)) { // 肯定是第一次安装，进入学习页
            appPrefs.saveJson(path, getFromAssets(path));
        }

    }

    //从assets 文件夹中获取文件并读取数据
    private String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = mContext.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer, "UTF-8");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
