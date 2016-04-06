package com.magicwindow.deeplink.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zxinsight.MWImageView;

/**
 * @author aaron
 * @date 16/2/26
 */
public class LearnAdapter extends PagerAdapter {
    Integer[] images;

    public LearnAdapter(Integer[] resId) {
        this.images = resId;
    }


    @Override
    public int getCount() {
        return images != null ? 0 : images.length;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup view, int position) {
        MWImageView imageView = new MWImageView(view.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        if (images != null && images.length > 0) {
            imageView.setImageResource(images[position]);
        }
        view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

}
