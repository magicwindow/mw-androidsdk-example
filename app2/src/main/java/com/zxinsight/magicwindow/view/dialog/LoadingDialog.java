package com.zxinsight.magicwindow.view.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zxinsight.magicwindow.R;
import com.zxinsight.magicwindow.activity.MWApplication;


/**
 * Created by Tony Shen on 15/12/1.
 */
public class LoadingDialog extends BaseDialog {

    private ImageView mLoadingImage;

    public LoadingDialog(Context context) {
        super(context, R.style.TranslucentDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MWApplication app = MWApplication.getInstance();
        View contentView = (View) ((LayoutInflater) app.getSystemService(MWApplication.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_loading, null);
        this.setContentView(contentView);
        this.setCanceledOnTouchOutside(false);

        mLoadingImage = (ImageView) contentView.findViewById(R.id.loadingImage);
        ((AnimationDrawable) mLoadingImage.getDrawable()).start();
    }
}
