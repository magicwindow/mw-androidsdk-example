package com.zxinsight.magicwindow.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zxinsight.MWImageView;
import com.zxinsight.magicwindow.config.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aaron
 * @date 16/2/26
 */
public class ImageAdapter extends PagerAdapter {
    List<Integer> list = new ArrayList<>();
    int mWPosition;

    public ImageAdapter(int mWPosition, List<Integer> res) {
        list = res;
//        imageLoader = MWApplication.getInstance().imageLoader;
        this.mWPosition = mWPosition;
    }

    /*public void addView(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((ImageView)object);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup view, int position) {
        MWImageView imageView = new MWImageView(view.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(list.get(position));
        imageView.bindEventWithMLink(Config.MWS[mWPosition+position],new JSONObject(),new JSONObject());
        view.addView(imageView,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

}
