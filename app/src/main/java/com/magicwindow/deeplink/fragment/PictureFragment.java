package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.PicturePresenter;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.Pic;
import com.magicwindow.deeplink.task.NetTask;

import java.util.List;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.adapter.SAFRecycleAdapter;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.rxjava.RxAsyncTask;
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

        if (app.session.get(Config.picList) != null) {
            mList = (List<Pic>) app.session.get(Config.picList);
            adapter.getList().addAll(mList);

            adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>() {

                @Override
                public Presenter call(ViewGroup parent, Integer integer) {

                    return new PicturePresenter(LayoutInflater.from(mContext).inflate(R.layout
                            .cell_picture, parent, false), mContext);
                }
            });
        } else { NetTask task = new NetTask(Config.picList);
            task.execute(new RxAsyncTask.HttpResponseHandler() {
                @Override
                public void onSuccess(String s) {
                    appPrefs.saveJson(Config.picList, s);
                    mList = appPrefs.getPicList();
                    app.session.put(Config.picList, mList);
                    adapter.getList().addAll(mList);
                    adapter.notifyDataSetChanged();
                    adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>() {

                        @Override
                        public Presenter call(ViewGroup parent, Integer integer) {
                            return new PicturePresenter(LayoutInflater.from(mContext).inflate(R
                                    .layout.cell_picture, parent, false), mContext);
                        }
                    });
                }

                @Override
                public void onFail(Throwable throwable) {
                    mList = appPrefs.getPicList();
                    adapter.getList().addAll(mList);

                    adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>() {

                        @Override
                        public Presenter call(ViewGroup parent, Integer integer) {

                            return new PicturePresenter(LayoutInflater.from(mContext).inflate(R
                                    .layout.cell_picture, parent, false), mContext);
                        }
                    });
                }
            });

        }

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void initView() {

        recyclerView.setAdapter(adapter);
        GridLayoutManager mgr = new GridLayoutManager(mContext, 2);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        onResume();
    }

}
