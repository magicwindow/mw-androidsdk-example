package com.magicwindow.deeplink.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.RecyclerViewItemClickListener;
import com.magicwindow.deeplink.activity.VideoDetailActivity;
import com.magicwindow.deeplink.adapter.PicturePresenter;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.domain.Pic;

import java.util.List;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.adapter.SAFRecycleAdapter;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import rx.functions.Func2;

/**
 * Created by Tony Shen on 15/11/25.
 */
public class PictureFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(id = R.id.main_content)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(id = R.id.id_grid_picture)
    RecyclerView recyclerView;

    SAFRecycleAdapter adapter = SAFRecycleAdapter.create();
    List<Pic> mList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        Injector.injectInto(this, view);
        mList = appPrefs.getPicList();
        adapter.getList().addAll(mList);

        adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>(){

            @Override
            public Presenter call(ViewGroup parent, Integer integer) {

                return new PicturePresenter(LayoutInflater.from(mContext).inflate(R.layout.cell_picture, parent, false),mContext);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void initView() {

        recyclerView.setAdapter(adapter);
        GridLayoutManager mgr = new GridLayoutManager(mContext, 2);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(mContext, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext, VideoDetailActivity.class));
            }
        }));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        onResume();
    }

}
