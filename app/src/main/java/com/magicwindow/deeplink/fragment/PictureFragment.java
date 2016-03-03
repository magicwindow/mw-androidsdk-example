package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.PicturePresenter;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.domain.Pic;
import com.magicwindow.deeplink.prefs.AppPrefs;

import java.util.List;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.adapter.SAFRecycleAdapter;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import rx.functions.Func2;

/**
 * Created by Tony Shen on 15/11/25.
 */
public class PictureFragment extends BaseFragment {

    @InjectView(id = com.magicwindow.deeplink.R.id.id_grid_picture)
    RecyclerView recyclerView;

    SAFRecycleAdapter adapter = SAFRecycleAdapter.create();
    List<Pic> mList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.magicwindow.deeplink.R.layout.fragment_picture, container, false);
        Injector.injectInto(this, view);

        initViews();
        return view;
    }

    private void initViews() {


        mList = AppPrefs.get(mContext).getPicList();
        adapter.getList().addAll(mList);

        adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>(){

            @Override
            public Presenter call(ViewGroup parent, Integer integer) {

                return new PicturePresenter(LayoutInflater.from(mContext).inflate(R.layout.cell_picture, parent, false),mContext);
            }
        });
        recyclerView.setAdapter(adapter);
        GridLayoutManager mgr = new GridLayoutManager(mContext, 2);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
    }


}
