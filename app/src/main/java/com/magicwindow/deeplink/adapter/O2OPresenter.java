package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.O2OItem;
import cn.magicwindow.MWImageView;
import cn.magicwindow.MarketingHelper;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by Tony Shen on 16/2/3.
 */
public class O2OPresenter extends Presenter<O2OItem> {

    @InjectView(id = R.id.id_o2o_list_img)
    MWImageView listBg;

    @InjectView(id = R.id.id_o2o_list_title)
    TextView title;

    @InjectView(id = R.id.id_o2o_list_desc)
    TextView desc;

    @InjectView(id = R.id.id_o2o_list_date)
    TextView date;

    public O2OPresenter(View view, Context context) {
        super(view);
        this.mContext = context;
    }

    @Override
    public void onBind(int position, O2OItem item) {
        if (item != null) {
            listBg.setImageResource(item.imgRes);
            title.setText(item.title);
            desc.setText(item.desc);
            date.setText(item.date);
        }

        //@mw mwOffset 是Config.MWS[]的偏移量，偏移12个后，为"YJMF5LRY",//13O2O-list01
        int mwOffset = 12 + position;
        if (mwOffset <= Config.MWS.length) {
            if (MarketingHelper.currentMarketing(mContext).isActive(Config.MWS[mwOffset])) {
                title.setText(MarketingHelper.currentMarketing(mContext).getTitle(Config.MWS[mwOffset]));
                desc.setText(MarketingHelper.currentMarketing(mContext).getDescription(Config.MWS[mwOffset]));
                MWImageView imageView = listBg;
                if (imageView != null) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.bindEvent(Config.MWS[mwOffset]);
                }
            }
        }
    }
}
