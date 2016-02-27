package com.zxinsight.magicwindow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxinsight.MWImageView;
import com.zxinsight.magicwindow.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aaron on 15/4/7.
 */
public class DetailListAdapter extends BaseAdapter {

    private ViewHolder holder;
    private Context mContext;
    private List<Map<String, Object>> mList;

    public DetailListAdapter(Context context, ArrayList list) {
        mContext = context;
        mList = list;

    }


    @Override
    public int getCount() {
//        Log.e("HomeListAdapter", "count = "+mList.size());
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
//        Log.e("HomeListAdapter", "position = "+position);

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_detail_list, viewGroup, false);
            holder = new ViewHolder();
            holder.icon = (MWImageView) view.findViewById(R.id.detail_icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.discard = (TextView) view.findViewById(R.id.discard);
            holder.soled = (TextView) view.findViewById(R.id.soled);
            view.setTag(holder);
        }

        Map map = mList.get(position);

        holder.title.setText((String) map.get("title"));
        holder.discard.setText(String.format(mContext.getString(R.string.discard), map.get("discard")));
        holder.soled.setText(String.format(mContext.getString(R.string.soled), map.get("soled")));
        holder.icon.setImageResource((Integer) map.get("category_icon"));

        return view;
    }

    private class ViewHolder {
        MWImageView icon;
        TextView title;
        TextView discard;
        TextView soled;

    }
}
