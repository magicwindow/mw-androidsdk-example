package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.activity.TourDetailActivity;
import com.magicwindow.deeplink.activity.WebViewActivity;
import com.magicwindow.deeplink.app.MWApplication;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.TravelList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import java.util.List;

import cn.salesuite.saf.adapter.SAFAdapter;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.utils.Preconditions;

/**
 * Created by aaron on 15/11/25.
 */
public class TourListAdapter extends SAFAdapter<TravelList.TravelContent> {

    int TYPE_DEFAULT = 0;
    int TYPE0 = 1;
    int TYPE_COUNT = TYPE0 + 1;
    private Context mContext;

    public TourListAdapter(Context context, List list) {
        mContext = context;
        mList = list;

    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position == 0 ? TYPE0 : TYPE_DEFAULT;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return TYPE_COUNT;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.cell_tour, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        int type = getItemViewType(position);
        final TravelList.TravelContent item = mList.get(position);
        if (item != null) {
//            holder.indicateView.setImageResource(item.imgRes);
            if (Preconditions.isNotBlank(item.title)) {
                holder.title.setText(item.title);
            }

            if (Preconditions.isNotBlank(item.desc)) {
                holder.desc.setText(item.desc);
            }

            //@mw mwOffset 是Config.MWS[]的偏移量，偏移4个后，为"4V2SVA7L",//5旅游-list01git
            final int mwOffset = 4 + position;

            if (type == TYPE0) {
                ImageLoader.getInstance().displayImage(item.resource, holder.indicateView);
                holder.indicateView.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, TourDetailActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            } else {
                if (mwOffset <= Config.MWS.length && MarketingHelper.currentMarketing(convertView.getContext()).isActive(Config.MWS[mwOffset])) {
                    holder.title.setText(MarketingHelper.currentMarketing(convertView.getContext()).getTitle(Config.MWS[mwOffset]));
                    holder.desc.setText(MarketingHelper.currentMarketing(convertView.getContext()).getDescription(Config.MWS[mwOffset]));
                    holder.indicateView.setScaleType(ImageView.ScaleType.FIT_XY);
                    holder.indicateView.bindEvent(Config.MWS[mwOffset]);

                    holder.indicateView.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[mwOffset]);
                        }
                    });
                } else {
                    ImageLoader.getInstance().displayImage(item.resource, holder.indicateView);
                    holder.indicateView.getRootView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.WEB_URL, item.url);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        }

        return convertView;
    }

    private class ViewHolder extends SAFViewHolder {

        @InjectView(id = R.id.list_img)
        MWImageView indicateView;

        @InjectView(id = R.id.list_title)
        TextView title;

        @InjectView(id = R.id.list_desc)
        TextView desc;

        public ViewHolder(View convertView) {
            super(convertView);
        }
    }

}
