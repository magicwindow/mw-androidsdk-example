package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.ui.ImageIndicatorView;
import cn.magicwindow.MLink;

import cn.salesuite.saf.inject.annotation.InjectExtra;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by Tony Shen on 16/2/19.
 */
public class LearnActivity extends BaseAppCompatActivity {

    public final static int FROM_SPLASH = 1;
    public final static int FROM_SETTING = 2;
    public static String TYPE = "type";

    @InjectView
    ImageIndicatorView indicateView;

    @InjectView
    ImageView experienceView;

    @InjectExtra
    int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initViews();
    }

    private void initViews() {
        Integer[] resArray = new Integer[]{R.drawable.learning1, R.drawable.learning2, R.drawable
                .learning3, R.drawable.learning4};
        indicateView.setupLayoutByDrawable(resArray);
        indicateView.setIndicateStyle(ImageIndicatorView.INDICATE_USERGUIDE_STYLE);
        indicateView.show();

        indicateView.setOnItemChangeListener(new ImageIndicatorView.OnItemChangeListener() {

            @Override
            public void onPosition(int position, int totalCount) {
                if (position == 3) {
                    experienceView.setVisibility(View.VISIBLE);
                } else {
                    experienceView.setVisibility(View.GONE);
                }
            }

        });

        experienceView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (type) {
                    case FROM_SPLASH:
                        loadingNext();
                        break;
                    case FROM_SETTING:
                        finish();
                        break;
                    default:
                        loadingNext();
                        break;
                }
            }

        });
    }

    /**
     * 跳转到主页面
     */
    private void loadingNext() {
        Intent i = new Intent(LearnActivity.this, MainActivity.class);
        startActivity(i);
        MLink.getInstance(this).deferredRouter();
        finish();
    }
}
