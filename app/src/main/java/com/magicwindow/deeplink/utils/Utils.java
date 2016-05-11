package com.magicwindow.deeplink.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.zxinsight.MWConfiguration;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件操作
 *
 * @author aaron
 * @since 14/6/19
 */
public class Utils {
    private static final String TAG = "FileUtils";

    /**
     * 文件保存路径
     */
    public static final String FILE_SAVE_PATH = "mw_cache";

    private Utils(){
        throw new RuntimeException("stop!");
    }

    public static String getMWCachePath(final Context context) {
        return createDiskCacheDir(context, FILE_SAVE_PATH).getPath() + File.separator;
    }


    /**
     * 执行拷贝任务
     *
     * @return 拷贝成功后的目标文件句柄
     * @throws IOException
     */
    public static String copyAssetsFile(Context context,String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in;
        OutputStream out;
        try {
            in = assetManager.open(filename);
            String newFileName = getMWCachePath(context) + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            return newFileName;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        return null;
    }


    /**
     * 检查权限是否开启
     *
     * @param permission
     * @return true or false
     */
    public static boolean checkPermissions(Context context, String permission) {

        if (context == null) {
            context = MWConfiguration.getContext();
        }
        PackageManager localPackageManager = context.getPackageManager();
        return localPackageManager.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    private static File createDiskCacheDir(final Context context, String fileDir) {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && checkPermissions(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), fileDir);
        } else {
            cacheDir = context.getCacheDir();
        }

        if (!cacheDir.exists()) {
            boolean b = cacheDir.mkdirs();
            if (!b) {
                cacheDir = context.getCacheDir();
                if (!cacheDir.exists())
                    cacheDir.mkdirs();
            }
        }

        return cacheDir;
    }

}
