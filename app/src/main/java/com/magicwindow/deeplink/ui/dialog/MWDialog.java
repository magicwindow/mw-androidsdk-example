package com.magicwindow.deeplink.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import cn.salesuite.saf.utils.SAFUtils;


/**
 * Created by aaron on 14/12/25.
 */
public class MWDialog extends Dialog implements View.OnClickListener {
    protected Context mContext;
    private String mWindowKey;

    public MWDialog(Context context, String windowKey) {
        super(context);
        this.mContext = context;

        this.mWindowKey = windowKey;
//        showOrDismiss();
    }

    public void showOrDismiss() {
        // 如果魔窗关闭，则不显示此dialog
        if (!isActive(mContext)||this.isShowing()) {
            this.dismiss();
        } else {
            show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示title
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);// 底部显示
        window.setBackgroundDrawable(new BitmapDrawable());// 去掉黑色背景

        setContentView(initView());
    }

    private LinearLayout initView() {
        LinearLayout mainLinear = new LinearLayout(mContext);
        mainLinear.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayoutParams.setMargins(0, 25, 0, 25);

        mainLinear.setLayoutParams(mainLayoutParams);
        MWImageView imageView = new MWImageView(mContext);
        imageView.bindEvent(mWindowKey);
        imageView.setClickable(false);

        mainLinear.setOnClickListener(this);
        mainLinear.addView(imageView);
        return mainLinear;
    }

    @Override
    public void onClick(View view) {
        if (SAFUtils.checkNetworkStatus(mContext)) {
            MarketingHelper.currentMarketing(mContext).click(mContext,
                    mWindowKey);

            dismiss();
        } else {
            Toast.makeText(mContext, "无网络连接，请查看您的网络情况", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private boolean isActive(Context context) {

        return !TextUtils.isEmpty(mWindowKey) && MarketingHelper.currentMarketing(context).isActive(mWindowKey);

    }
}
