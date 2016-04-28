package com.magicwindow.deeplink.domain;

/**
 * Created by aaron on 16/4/28.
 */
public class DownloadResponse extends HttpResponse<DownloadResponse.DownLoad> {

    public static class DownLoad {
        boolean upgrade;
        String newVersionUrl;
        boolean forceUpgrade;
    }
}
