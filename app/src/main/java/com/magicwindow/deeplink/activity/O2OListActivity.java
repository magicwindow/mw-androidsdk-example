package com.magicwindow.deeplink.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.O2OPresenter;
import com.magicwindow.deeplink.app.BaseAppCompatActivity;
import com.magicwindow.deeplink.domain.O2OItem;
import com.magicwindow.deeplink.ui.DividerItemDecoration;
import com.magicwindow.deeplink.ui.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.adapter.SAFRecyclerAdapter;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.utils.SAFUtils;
import rx.functions.Func2;

public class O2OListActivity extends BaseAppCompatActivity {

    @InjectView(id = R.id.o2o_container)
    RefreshLayout swipeContainer;

    @InjectView(id = R.id.o2o_list)
    RecyclerView recyclerView;

    @InjectView
    Toolbar toolbar;

    SAFRecyclerAdapter adapter = SAFRecyclerAdapter.create();
    List<O2OItem> mList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o2o_list);
        initViews();
    }

    private void initViews() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mList = new ArrayList<O2OItem>();

        O2OItem item1 = new O2OItem();
        item1.imgRes = R.drawable.avatar001;
        item1.title = "马名扬";
        item1.desc = "28岁，从业10年，高级按摩师，中国推拿协会会员";
        item1.date = "June 15";

        mList.add(item1);

        O2OItem item2 = new O2OItem();
        item2.imgRes = R.drawable.avatar002;
        item2.title = "张刚";
        item2.desc = "33岁，从事中医理疗多年，擅长颈椎、腰椎按摩";
        item2.date = "June 15";
        mList.add(item2);

        O2OItem item3 = new O2OItem();
        item3.imgRes = R.drawable.avatar002;
        item3.title = "李华";
        item3.desc = "35岁，从业8年，拥有大量临床理疗经验，手法娴熟";
        item3.date = "June 15";
        mList.add(item3);

        O2OItem item4 = new O2OItem();
        item4.imgRes = R.drawable.avatar002;
        item4.title = "章梅";
        item4.desc = "30岁，擅长中医推拿，经络调理，脏腑理疗，颈椎病，肩周炎";
        item4.date = "June 15";
        mList.add(item4);

        O2OItem item5 = new O2OItem();
        item5.imgRes = R.drawable.avatar002;
        item5.title = "王飞洋";
        item5.desc = "28岁，手法柔和，娴熟，渗透，细腻，擅长调理肩颈腰腿肌肉僵硬";
        item5.date = "June 15";
        mList.add(item5);

        adapter.getList().addAll(mList);
        adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>(){

            @Override
            public Presenter call(ViewGroup parent, Integer integer) {

                return new O2OPresenter(LayoutInflater.from(mContext).inflate(R.layout.cell_o2o_list, parent, false),mContext);
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        swipeContainer.setMoreData(false);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!SAFUtils.checkNetworkStatus(mContext)) {
                            toast(R.string.network_error);
                            return;
                        }

                        swipeContainer.hideFooterView();
                        swipeContainer.setLoading(false);

                        loadData();
                    }
                }, 2000);
            }

        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_purple, android.R.color.holo_orange_light);
    }

    private void loadData() {
        swipeContainer.setRefreshing(false);
    }
}
