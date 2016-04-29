package com.magicwindow.deeplink.domain;

import java.io.Serializable;

/**
 * Created by aaron on 16/4/28.
 */
public class DownloadResponse implements Serializable {

    public int code;
    public String msg;
    public DownLoad result;

    public static class DownLoad implements Serializable {
        public boolean upgrade;
        public String newVersionUrl;
        public boolean forceUpgrade;
    }
}
