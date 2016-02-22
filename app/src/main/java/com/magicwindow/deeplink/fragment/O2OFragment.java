package com.magicwindow.deeplink.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magicwindow.deeplink.activity.O2OListActivity;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;
import com.zxinsight.TrackAgent;
import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.ui.ImageIndicatorView;

import java.util.HashMap;

import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.log.L;

public class O2OFragment extends BaseFragment {

    @InjectView(id = R.id.o2o_imageindicatorview)
    ImageIndicatorView banner;

    @InjectView(id = R.id.top_1_layout)
    RelativeLayout top_1_layout;
    @InjectView(id = R.id.top_1)
    TextView top_1;
    @InjectView(id = R.id.o2o_icon01)
    MWImageView ic_top_1;

    @InjectView(id = R.id.top_2_layout)
    RelativeLayout top_2_layout;
    @InjectView(id = R.id.top_2)
    TextView top_2;
    @InjectView(id = R.id.o2o_icon02)
    MWImageView ic_top_2;

    @InjectView(id = R.id.top_3_layout)
    RelativeLayout top_3_layout;
    @InjectView(id = R.id.top_3)
    TextView top_3;
    @InjectView(id = R.id.o2o_icon03)
    MWImageView ic_top_3;

    @InjectView(id = R.id.top_4_layout)
    RelativeLayout top_4_layout;
    @InjectView(id = R.id.top_4)
    TextView top_4;
    @InjectView(id = R.id.o2o_icon04)
    MWImageView ic_top_4;

    @InjectView(id = R.id.o2o_img_1)
    MWImageView img_1;

    @InjectView(id = R.id.o2o_img_2)
    MWImageView img_2;

    @InjectView(id = R.id.o2o_img_3)
    MWImageView img_3;

//    @InjectView(id = R.id.o2o_list)
//    ListViewForScrollView o2oList;

    private MarketingHelper marketingHelper;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            L.e("Eo2oFragment visible");
            TrackAgent.currentEvent().onPageStart("电商");

        } else {
            L.e("Eo2oFragment invisible");
            TrackAgent.currentEvent().onPageEnd("电商");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_o2o, container, false);
        Injector.injectInto(this, view);

        marketingHelper = MarketingHelper.currentMarketing(mContext);

        initView();
        bindMW();

        return view;
    }

    private void initView() {


        Integer[] imgResArray = new Integer[]{R.drawable.o2o_banner001, R.drawable.o2o_banner002, R.drawable.o2o_banner003, R.drawable.o2o_banner004};
       /* O2OListAdapter adapter = new O2OListAdapter(mContext);
        o2oList.setAdapter(adapter);

        o2oList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));
            }
        });*/

        banner.setupLayoutByDrawable(imgResArray);
        banner.setIndicateStyle(ImageIndicatorView.INDICATE_ARROW_ROUND_STYLE);
//
        banner.show();

        banner.setOnItemClickListener(new ImageIndicatorView.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));
            }
        });

        ic_top_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));

            }
        });
        ic_top_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));

            }
        });

        ic_top_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));

            }
        });
        ic_top_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));

            }
        });
        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));
            }
        });
        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, O2OListActivity.class));
            }
        });
    }

    private void bindMW() {
        banner.getView(0).bindEvent(Config.MWS[64]);
        banner.getView(0).setScaleType(ImageView.ScaleType.FIT_XY);
        banner.getView(1).bindEvent(Config.MWS[65]);
        banner.getView(1).setScaleType(ImageView.ScaleType.FIT_XY);
        banner.getView(2).bindEvent(Config.MWS[66]);
        banner.getView(2).setScaleType(ImageView.ScaleType.FIT_XY);
        banner.getView(3).bindEvent(Config.MWS[67]);
        banner.getView(3).setScaleType(ImageView.ScaleType.FIT_XY);


        //采用自定义方法
        if (marketingHelper.isActive(Config.MWS[68])) {
            top_1.setText(marketingHelper.getTitle(Config.MWS[68]));
            ic_top_1.bindEvent(Config.MWS[68]);
            top_1_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marketingHelper.click(mContext, Config.MWS[68]);
                }
            });
        }

        //采用自定义方法
        if (marketingHelper.isActive(Config.MWS[69])) {
            top_2.setText(marketingHelper.getTitle(Config.MWS[69]));
            ic_top_2.bindEvent(Config.MWS[69]);
            top_2_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marketingHelper.click(mContext, Config.MWS[69]);
                }
            });
        }

        //采用自定义方法
        if (marketingHelper.isActive(Config.MWS[70])) {
            top_3.setText(marketingHelper.getTitle(Config.MWS[70]));
            ic_top_3.bindEvent(Config.MWS[70]);
            top_3_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marketingHelper.click(mContext, Config.MWS[70]);
                }
            });
        }

        //采用自定义方法
        if (marketingHelper.isActive(Config.MWS[71])) {
            top_4.setText(marketingHelper.getTitle(Config.MWS[71]));
            ic_top_4.bindEvent(Config.MWS[71]);
            top_4_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    marketingHelper.click(mContext, Config.MWS[71]);
                }
            });
        }

        //middle第1个魔窗位
        img_1.bindEvent(Config.MWS[72]);
        //middle第2个魔窗位
        HashMap<String, String> dt = new HashMap<String, String>();
        dt.put("name1","青岛啤酒");
        dt.put("name2","雪花啤酒");
        dt.put("name3","五粮液");
        img_2.bindEventWithMLink(Config.MWS[73],dt);
        //middle第3个魔窗位
        //middle第2个魔窗位
        HashMap<String, String> dt1 = new HashMap<String, String>();
        dt1.put("name1","剑南春");
        dt1.put("name2","茅台酒");
        dt1.put("name3","梦之蓝");
        img_3.bindEventWithMLink(Config.MWS[74],dt1);

    }

    @Override
    public void onPause() {
//        TrackAgent.currentEvent().onPageEnd("主页");

        super.onPause();
    }

    @Override
    public void onResume() {
//        TrackAgent.currentEvent().onPageStart("主页");

        super.onResume();
    }

}