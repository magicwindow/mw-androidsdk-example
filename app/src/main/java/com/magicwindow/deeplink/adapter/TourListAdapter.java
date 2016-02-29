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
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.HomeItem;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import java.util.ArrayList;

import cn.salesuite.saf.adapter.SAFAdapter;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.utils.Preconditions;

/**
 * Created by aaron on 15/11/25.
 */
public class TourListAdapter extends SAFAdapter<HomeItem> {

    private final String[] listStrings;
    private Context mContext;

    public TourListAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<HomeItem>();

        HomeItem item1 = new HomeItem();
        item1.imgRes = R.drawable.tour_list_01;
        item1.title = "东方的夏威夷";
        item1.desc = "美丽三亚，浪漫天涯";
        mList.add(item1);

        HomeItem item2 = new HomeItem();
        item2.imgRes = R.drawable.tour_list_09;
        item2.title = "京都动物园一日游";
        item2.desc = "在繁华的京都中感受另一个日本";
        mList.add(item2);

        HomeItem item3 = new HomeItem();
        item3.imgRes = R.drawable.tour_list_03;
        item3.title = "绿竹林里的静谧";
        item3.desc = "走在路上，感受心灵的净化";
        mList.add(item3);

        HomeItem item4 = new HomeItem();
        item4.imgRes = R.drawable.tour_list_04;
        item4.title = "灌篮高手的回忆";
        item4.desc = "年少时的那个篮球梦你还记得么？";
        mList.add(item4);

        HomeItem item5 = new HomeItem();
        item5.imgRes = R.drawable.tour_list_05;
        item5.title = "夜色·星空";
        item5.desc = "星空下的大草原";
        mList.add(item5);

        HomeItem item6 = new HomeItem();
        item6.imgRes = R.drawable.tour_list_06;
        item6.title = "雪山下的油菜花";
        item6.desc = "一场静谧的旅行，一片黄色的花海";
        mList.add(item6);

        HomeItem item7 = new HomeItem();
        item7.imgRes = R.drawable.tour_list_07;
        item7.title = "娜鲁萱岛度假村";
        item7.desc = "梦中的伊甸园之菲律宾之旅";
        mList.add(item7);

        HomeItem item8 = new HomeItem();
        item8.imgRes = R.drawable.tour_list_08;
        item8.title = "一场说走就走的旅行";
        item8.desc = "记录菲律宾海滨8日游";
        mList.add(item8);

        listStrings = new String[]{
                "http://m.mafengwo.cn/travel-news/236997.html",
                "http://blog.sina.com.cn/s/blog_83a1adc801019sw0.html",
                "http://yf82227537.blog.163.com/blog/static/11497776120157308541818/",
                "http://m.mafengwo.cn/i/3187584.html",
                "http://m.mafengwo.cn/travel-news/219893.html",
                "http://m.mafengwo.cn/z/1003.html",
                "http://m.mafengwo.cn/i/2996095.html",
                "http://m.ctrip.com/html5/you/travels/sichuan100009/1732894.html",
                "http://m.mafengwo.cn/travel-news/236997.html"
        };
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.cell_tour, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        HomeItem item = mList.get(position);
        if (item != null) {
            holder.indicateView.setImageResource(item.imgRes);

            if (Preconditions.isNotBlank(item.title)) {
                holder.title.setText(item.title);
            }

            if (Preconditions.isNotBlank(item.desc)) {
                holder.desc.setText(item.desc);
            }

            //@mw mwOffset 是Config.MWS[]的偏移量，偏移4个后，为"4V2SVA7L",//5旅游-list01git
            final int mwOffset = 4 + position;

            if (position == 0) {
                holder.indicateView.setOnClickListener(new View.OnClickListener() {
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

                    holder.indicateView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MarketingHelper.currentMarketing(mContext).click(mContext, Config.MWS[mwOffset]);
                        }
                    });
                } else {
                    holder.indicateView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.WEB_URL, listStrings[position]);
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
