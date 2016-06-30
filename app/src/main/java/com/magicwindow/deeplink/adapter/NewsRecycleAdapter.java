package com.magicwindow.deeplink.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.activity.NewsDetailActivity;
import com.magicwindow.deeplink.activity.WebViewActivity;
import com.magicwindow.deeplink.app.MWApplication;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.NewsList;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import java.util.List;

/**
 * @author aaron
 * @date 15/11/25
 */
public class NewsRecycleAdapter extends RecyclerView.Adapter<NewsRecycleAdapter.ViewHolder> {


    int TYPE_DEFAULT = 0;
    int TYPE0 = 1;
    int TYPE_COUNT = TYPE0 + 1;

    protected List<NewsList.NewsContent> mList = null;

    public NewsRecycleAdapter(List<NewsList.NewsContent> items) {
        mList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_news, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position == 0 ? TYPE0 : TYPE_DEFAULT;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewsList.NewsContent item = mList.get(position);
        int type = getItemViewType(position);

        if (type == TYPE0) {
            holder.title.setText("小而美的综合体");
            holder.desc.setText("iPhone SE深度体验");
            holder.listBg.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.listBg.getContext(), NewsDetailActivity.class);
                    holder.listBg.getContext().startActivity(intent);
                }
            });
        } else {
            if (item != null) {
                holder.listBg.setImageResource(R.drawable.news01);
                MWApplication.getInstance().imageLoader.displayImage(item.resource, holder.listBg);
                holder.title.setText(item.title);
                holder.desc.setText(item.desc);
                int a = mList.get(0).mwKey + position;
                if (MarketingHelper.currentMarketing(holder.listBg.getContext()).isActive(Config.MWS[mList.get(0).mwKey + position])) {
                    holder.listBg.bindEvent(Config.MWS[mList.get(0).mwKey + position]);
                    holder.title.setText(MarketingHelper.currentMarketing(holder.title.getContext()).getTitle(Config.MWS[mList.get(0).mwKey + position]));
                    holder.desc.setText(MarketingHelper.currentMarketing(holder.desc.getContext()).getDescription(Config.MWS[mList.get(0).mwKey + position]));
                }
                holder.listBg.getRootView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.listBg.getContext(), WebViewActivity.class);
                        intent.putExtra(WebViewActivity.WEB_URL, item.url);
                        holder.listBg.getContext().startActivity(intent);
                    }
                });
            }
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        MWImageView listBg;
        TextView title;
        TextView desc;

        public ViewHolder(View view) {
            super(view);
            listBg = (MWImageView) view.findViewById(R.id.id_news_list_img);
            title = (TextView) view.findViewById(R.id.id_news_list_title);
            desc = (TextView) view.findViewById(R.id.id_news_list_desc);
        }
    }

}
