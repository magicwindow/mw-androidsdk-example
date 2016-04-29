package com.magicwindow.deeplink.download;

import android.app.ProgressDialog;
import android.content.Context;

import cn.salesuite.saf.download.DownloadTask;
import cn.salesuite.saf.download.DownloadTaskListener;
import cn.salesuite.saf.utils.SAFUtils;
import cn.salesuite.saf.utils.ToastUtils;

/**
 * Created by Tony Shen on 16/4/29.
 */
public class UpdateDownloadTaskListener extends DownloadTaskListener {

    private ProgressDialog pBar;
    private Context mContext;
    private String apkPathUrl;

    public UpdateDownloadTaskListener(ProgressDialog pBar, Context mContext, String apkPathUrl) {
        this.pBar = pBar;
        this.mContext = mContext;
        this.apkPathUrl = apkPathUrl;
    }

    @Override
    public void updateProcess(DownloadTask mgr) {
        if (mgr != null) {
            pBar.setProgress((int) mgr.getDownloadSize());
            if (mgr.getDownloadPercent() == 0 && mgr.getTotalSize() != 0) {
                pBar.setMax((int) mgr.getTotalSize());
            }
        }
    }

    @Override
    public void preDownload(DownloadTask mgr) {
        if (pBar == null) {
            pBar = new ProgressDialog(mContext);
            pBar.setTitle("正在下载");
            pBar.setMessage("请稍候...");
            pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pBar.setCancelable(false);
        }
        pBar.show();
    }

    @Override
    public void pauseProcess(DownloadTask mgr) {
        // nothing
    }

    @Override
    public void finishDownload(DownloadTask mgr) {
        SAFUtils.installAPK(apkPathUrl, mContext);
        pBar.cancel();
    }

    @Override
    public void errorDownload(DownloadTask mgr, int error) {
        ToastUtils.showLong(mContext, "下载发生错误");
    }

}
