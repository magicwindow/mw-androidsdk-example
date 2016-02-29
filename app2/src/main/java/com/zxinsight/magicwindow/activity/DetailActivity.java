package com.zxinsight.magicwindow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zxinsight.MWImageView;
import com.zxinsight.TrackAgent;
import com.zxinsight.magicwindow.config.Config;
import com.zxinsight.magicwindow.adapter.DetailListAdapter;
import com.zxinsight.magicwindow.view.ListViewForScrollView;
import com.zxinsight.magicwindow.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends BaseAppCompatActivity {

    ListViewForScrollView mListView;
    String name1 = "Chateau Cheval Blanc";
    String name2 = "Mouton Rothschild";
    String name3 = "Chateau Rayas";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolBar();

        Button attend_immediately = (Button) findViewById(R.id.attend_immediately);
        Intent intent = getIntent();
        if (intent != null) {
            String join = intent.getStringExtra("key");
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
            if (!TextUtils.isEmpty(toast)) {
                Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            }
            if (!TextUtils.isEmpty(join)) {
                attend_immediately.setText(join);
            }
        }

        initView();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_navigation);
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
        TextView groupBuyTitle = (TextView) findViewById(R.id.group_buy);
        groupBuyTitle.setText(String.format(getString(R.string.group_buy), "3"));
        MWImageView detailTop = (MWImageView) findViewById(R.id.detail_top_image);
        detailTop.bindEvent(Config.MW_DETAIL);
        mListView = (ListViewForScrollView) findViewById(R.id.listView);

        mListView.setAdapter(new DetailListAdapter(this, getList()));
    }

    private ArrayList<Map<String, Object>> getList() {
        ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("category_icon", R.drawable.detail_1);
        map1.put("discard", "5");
        map1.put("soled", "1265");
        map1.put("title", name1);
        mList.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("category_icon", R.drawable.detail_2);
        map2.put("discard", "7");
        map2.put("soled", "1565");
        map2.put("title", name2);
        mList.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();

        map3.put("category_icon", R.drawable.detail_3);
        map3.put("discard", "6");
        map3.put("soled", "1324");
        map3.put("title", name3);
        mList.add(map3);
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
