package com.magicwindow.deeplink.prefs;

import android.content.Context;
import android.text.TextUtils;

import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.BusinessList;
import com.magicwindow.deeplink.domain.NewsList;
import com.magicwindow.deeplink.domain.O2ODetail;
import com.magicwindow.deeplink.domain.O2OList;
import com.magicwindow.deeplink.domain.Pic;
import com.magicwindow.deeplink.domain.ShopDetail;
import com.magicwindow.deeplink.domain.TravelDetail;
import com.magicwindow.deeplink.domain.TravelList;

import java.io.IOException;
import java.util.ArrayList;

import cn.salesuite.saf.http.rest.RestUtil;
import cn.salesuite.saf.prefs.BasePrefs;

/**
 * Created by Tony Shen on 15/12/14.
 */
public class AppPrefs extends BasePrefs {

    private static final String PREFS_NAME = "AppPrefs";

    private static final String LAST_VERSION = "last_version";
    private static final String GUIDE_TOUR = "guide_tour";
    private static final String GUIDE_EBUSINESS = "guide_ebusiness";
    private static final String CLIENT_ID = "clientid";

    private AppPrefs(Context context) {
        super(context, PREFS_NAME);
    }

    public static AppPrefs get(Context context) {
        if (context == null) {
            return null;
        } else {
            return new AppPrefs(context.getApplicationContext());
        }
    }

    public String getCid() {
        return getString(CLIENT_ID,null);
    }

    public void setCid(String v) {
        putString(CLIENT_ID, v);
    }

    public String getLastVersion() {
        return getString(LAST_VERSION, null);
    }

    public void setLastVersion(String v) {
        putString(LAST_VERSION, v);
    }

    public boolean getGuideTour() {
        return getBoolean(GUIDE_TOUR, false);
    }

    public void setGuideTour(boolean flag) {
        putBoolean(GUIDE_TOUR, flag);
    }

    public boolean getGuideEbusiness() {
        return getBoolean(GUIDE_EBUSINESS, false);
    }

    public void setGuideEbusiness(boolean flag) {
        putBoolean(GUIDE_EBUSINESS, flag);
    }

    public void saveJson(String key, String value) {
        putString(key, value);
    }

    public BusinessList getBusiness() {
        BusinessList list = new BusinessList();
        String business = getString(Config.businessList, "");
        if (!TextUtils.isEmpty(business)) {
            try {
                list = RestUtil.parseAs(BusinessList.class, business);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public NewsList getNewsList() {
        NewsList list = new NewsList();
        String business = getString(Config.newsList, "");
        if (!TextUtils.isEmpty(business)) {
            try {
                list = RestUtil.parseAs(NewsList.class, business);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public O2OList getO2OList() {
        O2OList list = new O2OList();
        String business = getString(Config.o2oList, "");
        if (isOK(business)) {
            try {
                list = RestUtil.parseAs(O2OList.class, business);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ArrayList<Pic> getPicList() {
        ArrayList<Pic> list = new ArrayList<Pic>();
        String business = getString(Config.picList, "");
        if (isOK(business)) {
            try {
                list = RestUtil.parseArray(Pic.class, business);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public TravelList getTravelList() {
        TravelList list = new TravelList();
        String business = getString(Config.travelList, "");
        if (isOK(business)) {
            try {
                list = RestUtil.parseAs(TravelList.class, business);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public ShopDetail getShopDetail() {
        ShopDetail detail = new ShopDetail();
        String shopDetail = getString(Config.shopDetail, "");
        if (isOK(shopDetail)) {
            try {
                detail = RestUtil.parseAs(ShopDetail.class, shopDetail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return detail;
    }

    public O2ODetail getO2ODetail() {
        O2ODetail detail = new O2ODetail();
        String o2oDetail = getString(Config.o2oDetail, "");
        if (isOK(o2oDetail)) {
            try {
                detail = RestUtil.parseAs(O2ODetail.class, o2oDetail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return detail;
    }

    public TravelDetail getTravelDetail() {
        TravelDetail detail = new TravelDetail();
        String travelDetail = getString(Config.travelDetail, "");
        if (isOK(travelDetail)) {
            try {
                detail = RestUtil.parseAs(TravelDetail.class, travelDetail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return detail;
    }

    private boolean isOK(String httpResponse) {

        return httpResponse != null && (httpResponse.startsWith("{") || httpResponse.startsWith("["));
    }

}
