package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.MWApplication;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.BusinessList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import java.util.List;

import cn.salesuite.saf.adapter.SAFAdapter;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by aaron on 15/11/25.
 */
public class BusinessListAdapter extends SAFAdapter<BusinessList.BusinessContent> {

    private Context mContext;

    public BusinessListAdapter(Context context, List<BusinessList.BusinessContent> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.cell_business, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        BusinessList.BusinessContent item = mList.get(position);
        if (item != null) {
//            holder.imageView.setImageResource(item.imgRes);
            ImageLoader.getInstance().displayImage(item.resource,holder.imageView);
            holder.title.setText(item.title);
        }

        //@mw mwOffset 是Config.MWS[]的偏移量，偏移38个后为"CTVGCCX4",//39电商-list01
        int mwOffset = 38 + position;
        if (mwOffset <= Config.MWS.length) {

            if (MarketingHelper.currentMarketing(convertView.getContext()).isActive(Config.MWS[mwOffset])) {
                holder.title.setText(MarketingHelper.currentMarketing(convertView.getContext()).getTitle(Config.MWS[mwOffset]));
                MWImageView imageView = holder.imageView;
                if (imageView != null) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.bindEvent(Config.MWS[mwOffset]);
                }
            }
        }

        return convertView;
    }

    private class ViewHolder extends SAFViewHolder {

        @InjectView(id = R.id.list_img)
        MWImageView imageView;

        @InjectView(id = R.id.list_title)
        TextView title;

        public ViewHolder(View convertView) {
            super(convertView);
        }
    }

}
