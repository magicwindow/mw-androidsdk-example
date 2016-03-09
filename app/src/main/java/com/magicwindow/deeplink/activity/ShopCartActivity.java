package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.ShoppingCartPresenter;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.ShoppingCartItem;
import com.magicwindow.deeplink.ui.DividerItemDecoration;
import com.zxinsight.TrackAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.adapter.SAFRecycleAdapter;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.inject.annotation.OnClick;
import rx.functions.Func2;

public class ShopCartActivity extends BaseAppCompatActivity {

    @InjectView(id = R.id.shop_cart_list)
    RecyclerView recyclerView;

    @InjectView
    Toolbar toolbar;

    SAFRecycleAdapter adapter = SAFRecycleAdapter.create();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initViews();
        initData();
    }

    private void initViews() {
        toolbar.setTitle(R.string.shop_cart);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData() {
        List<ShoppingCartItem>  list = new ArrayList<ShoppingCartItem>();

        ShoppingCartItem item1 = new ShoppingCartItem();
        item1.imgRes = R.drawable.order_address_img;
        item1.title = getString(R.string.nipple_title);
        item1.desc = getString(R.string.color_white);
        item1.price = getString(R.string.price);
        list.add(item1);

        adapter.getList().addAll(list);
        adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>() {

            @Override
            public Presenter call(ViewGroup parent, Integer integer) {

                return new ShoppingCartPresenter(LayoutInflater.from(mContext).inflate(R.layout.cell_shop_cart, parent, false), mContext);
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @OnClick(id = R.id.click_to_buy)
    public void clickBuy() {
        HashMap map = new HashMap();
        map.put("goods","婴儿奶嘴");
        map.put("price","14.49");
        TrackAgent.currentEvent().customEvent(Config.CUSTOM_CONFIRM_GOODS,map);
        startActivity(new Intent(mContext, ShopOrderActivity.class));
    }
}
