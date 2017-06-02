package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.domain.ShoppingCartItem;
import cn.magicwindow.MWImageView;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * @author Aaron
 * @date 16/2/28
 */
public class ShoppingCartPresenter extends Presenter<ShoppingCartItem> {

    @InjectView(id = R.id.id_list_img)
    MWImageView listBg;

    @InjectView(id = R.id.id_news_list_title)
    TextView title;

    @InjectView(id = R.id.id_list_desc)
    TextView desc;

    @InjectView(id = R.id.id_list_price)
    TextView price;

    public ShoppingCartPresenter(View view, Context context) {
        super(view);
        this.mContext = context;
    }

    @Override
    public void onBind(int position, ShoppingCartItem item) {
        if (item != null) {
            listBg.setImageResource(item.imgRes);
            title.setText(item.title);
            desc.setText(item.desc);
            price.setText(item.price);
        }
    }
}
