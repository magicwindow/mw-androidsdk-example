package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.Pic;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by Tony Shen on 16/2/2.
 */
public class PicturePresenter extends Presenter<Pic> {

    @InjectView(id = R.id.id_picture_list_bg)
    MWImageView listBg;

    @InjectView(id = R.id.id_picture_list_title)
    TextView title;

    @InjectView(id = R.id.id_picture_list_desc)
    TextView desc;


    public PicturePresenter(View view, Context context) {
        super(view);
        this.mContext = context;
    }

    @Override
    public void onBind(int position, final Pic item) {
        if (item != null) {
            listBg.setImageResource(R.drawable.pic_01);
            ImageLoader.getInstance().displayImage(item.resource, listBg);
            title.setText(item.title);
            desc.setText(item.desc);
        }

        //@mw mwOffset 是Config.MWS[]的偏移量，偏移17个后，为"GNOCMU9Y",//18图库-pic01
        int mwOffset = 17 + position;
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
