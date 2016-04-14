package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.LearnAdapter;
import com.magicwindow.deeplink.app.BaseActivity;

import cn.salesuite.saf.inject.annotation.InjectExtra;
import cn.salesuite.saf.inject.annotation.InjectView;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Tony Shen on 16/2/19.
 */
public class LearnActivity extends BaseActivity {

    public final static int FROM_SPLASH = 1;
    public final static int FROM_SETTING = 2;
    public static String TYPE = "type";

//    @InjectView
//    ImageIndicatorView indicateView;

    @InjectView(id = R.id.viewpager)
    ViewPager viewPager;

    @InjectView(id = R.id.indicator)
    CircleIndicator indicator;
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
        LearnAdapter adapter = new LearnAdapter(resArray);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 3) {
                    experienceView.setVisibility(View.VISIBLE);
                } else {
                    experienceView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setViewPager(viewPager);

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
        finish();
    }
}
