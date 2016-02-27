package com.zxinsight.magicwindow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;
import com.zxinsight.magicwindow.config.Config;
import com.zxinsight.magicwindow.domain.HomeItem;
import com.zxinsight.magicwindow.R;

import java.util.ArrayList;


/**
 * Created by aaron on 15/11/25.
 */
public class BusinessListAdapter extends BaseAdapter {

    private final ArrayList<HomeItem> mList;
    private Context mContext;

    public BusinessListAdapter(Context context) {
        mContext = context;
//        MarketingHelper marketingHelper = MarketingHelper.currentMarketing(mContext);
        mList = new ArrayList<HomeItem>();

        HomeItem item1 = new HomeItem();
//        item1.imgRes = new Integer[]{R.drawable.tour_list_01, R.drawable.tour_list_08};
        item1.imgRes = R.drawable.business_list001;
        item1.title = "美国嘉宝米粉零食专场";
        mList.add(item1);

        HomeItem item2 = new HomeItem();
        item2.imgRes = R.drawable.business_list002;
        item2.title = "母婴用品最强备货季";
        mList.add(item2);

        HomeItem item3 = new HomeItem();
        item3.imgRes = R.drawable.business_list003;
        item3.title = "海量童装春款上新";
        mList.add(item3);

        HomeItem item4 = new HomeItem();
        item4.imgRes = R.drawable.business_list004;
        item4.title = "儿童营养品终极狂欢";
        mList.add(item4);

        HomeItem item5 = new HomeItem();
        item5.imgRes = R.drawable.business_list005;
        item5.title = "六一儿童狂欢购";
        mList.add(item5);

        HomeItem item6 = new HomeItem();
        item6.imgRes = R.drawable.business_list006;
        item6.title = "母婴商品量贩囤货周";
        mList.add(item6);

        HomeItem item7 = new HomeItem();
        item7.imgRes = R.drawable.business_list007;
        item7.title = "儿童玩具动漫馆";
        mList.add(item7);

        HomeItem item8 = new HomeItem();
        item8.imgRes = R.drawable.business_list008;
        item8.title = "7大品牌奶粉新年惠";
        mList.add(item8);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mList == null ? null : (position > this.mList.size() - 1 ? null : this.mList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.cell_business, parent, false);
            holder = new ViewHolder();
            holder.imageView = (MWImageView) convertView.findViewById(R.id.list_img);
            holder.title = (TextView) convertView.findViewById(R.id.list_title);
            convertView.setTag(holder);
        }
        HomeItem item = mList.get(position);
        if (item != null) {
            holder.imageView.setImageResource(item.imgRes);
            holder.title.setText(item.title);
        }

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

    private class ViewHolder {

        //        @InjectView(id = R.id.list_img)
        MWImageView imageView;

        //        @InjectView(id = R.id.list_title)
        TextView title;

//        public ViewHolder(View convertView) {
//            super(convertView);
//        }
    }

}
