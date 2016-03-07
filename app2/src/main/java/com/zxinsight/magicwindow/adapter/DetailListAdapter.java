package com.zxinsight.magicwindow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxinsight.MWImageView;
import com.zxinsight.magicwindow.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by aaron on 15/4/7.
 */
public class DetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private final View mHeader;
    private ArrayList<Map<String, Object>> mList;

    public DetailListAdapter(ArrayList<Map<String, Object>> list, View header) {
        mList = list;
        this.mHeader = header;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mList.isEmpty() ? 0 : mList.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new DetailBannerViewHolder(mHeader);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_list, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (!isHeader(position)) {
            Map map = getItem(position);

            ((DetailViewHolder)holder).title.setText((String) map.get("title"));
            ((DetailViewHolder)holder).discard.setText(String.format(((DetailViewHolder)holder).discard.getContext().getString(R.string.discard), map.get("desc")));
            ((DetailViewHolder)holder).icon.setImageResource((Integer) map.get("category_icon"));
        }
    }

    private Map<String, Object> getItem(int position) {
        return mList.get(position - 1);
    }


    public class DetailBannerViewHolder extends RecyclerView.ViewHolder {
        MWImageView icon;

        public DetailBannerViewHolder(View itemView) {
            super(itemView);
            icon = (MWImageView) itemView;
            icon.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        MWImageView icon;
        TextView title;
        TextView discard;

        public DetailViewHolder(View itemView) {
            super(itemView);
            icon = (MWImageView) itemView.findViewById(R.id.detail_icon);
            title = (TextView) itemView.findViewById(R.id.title);
            discard = (TextView) itemView.findViewById(R.id.desc);
        }
    }
}
