package com.magicwindow.deeplink.prefs;

import android.content.Context;

import cn.salesuite.saf.prefs.BasePrefs;

/**
 * Created by Tony Shen on 15/12/14.
 */
public class AppPrefs extends BasePrefs {

    private static final String PREFS_NAME = "AppPrefs";

    private static final String LAST_VERSION = "last_version";
    private static final String GUIDE_TOUR = "guide_tour";
    private static final String GUIDE_EBUSINESS = "guide_ebusiness";

    private AppPrefs(Context context) {
        super(context, PREFS_NAME);
    }

    public static AppPrefs get(Context context) {
        return new AppPrefs(context);
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
}
