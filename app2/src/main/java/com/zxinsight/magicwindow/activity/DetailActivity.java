package com.zxinsight.magicwindow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zxinsight.MWImageView;
import com.zxinsight.TrackAgent;
import com.zxinsight.magicwindow.R;
import com.zxinsight.magicwindow.adapter.DetailListAdapter;
import com.zxinsight.magicwindow.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends BaseAppCompatActivity {

    RecyclerView recyclerView;
    String name1;
    String name2;
    String name3;
    String name4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolBar();

        name1 = getString(R.string.detail_title1);
        name2 = getString(R.string.detail_title2);
        name3 = getString(R.string.detail_title3);
        name4 = getString(R.string.detail_title4);

        Intent intent = getIntent();

        if (intent != null) {
            String toast = intent.getStringExtra("user");
            if (!TextUtils.isEmpty(intent.getStringExtra("name1"))) {
                name1 = intent.getStringExtra("name1");
            }
            if (!TextUtils.isEmpty(intent.getStringExtra("name2"))) {
                name2 = intent.getStringExtra("name2");
            }
            if (!TextUtils.isEmpty(intent.getStringExtra("name3"))) {
                name3 = intent.getStringExtra("name3");
            }
            if (!TextUtils.isEmpty(intent.getStringExtra("name4"))) {
                name4 = intent.getStringExtra("name4");
            }
            if (!TextUtils.isEmpty(toast)) {
                Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            }

        }

        initView();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.detail);

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

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MWImageView header = new MWImageView(this);
        header.setImageResource(R.drawable.detail_banner);
        final DetailListAdapter adapter = new DetailListAdapter(getList(), header);

        recyclerView.setAdapter(adapter);
        int spacing = 50;
        recyclerView.addItemDecoration(new DividerGridItemDecoration(spacing));
        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

    }

    private ArrayList<Map<String, Object>> getList() {
        ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("category_icon", R.drawable.detail01);
        map1.put("desc", getString(R.string.detail_price1));
        map1.put("title", name1);
        mList.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("category_icon", R.drawable.detail02);
        map2.put("desc", getString(R.string.detail_price2));
        map2.put("title", name2);
        mList.add(map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("category_icon", R.drawable.detail03);
        map3.put("desc", getString(R.string.detail_price3));
        map3.put("title", name3);
        mList.add(map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("category_icon", R.drawable.detail04);
        map4.put("desc", getString(R.string.detail_price4));
        map4.put("title", name4);
        mList.add(map4);
        return mList;

    }

    @Override
    public void onPause() {
        TrackAgent.currentEvent().onPageEnd("详情页");
        super.onPause();
    }

    @Override
    public void onResume() {
        TrackAgent.currentEvent().onPageStart("详情页");

        super.onResume();
    }


}
