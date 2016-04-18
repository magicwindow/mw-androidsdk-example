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
 * Created by Aaron on 16/04/18.
 */
public class ContactUsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        Injector.injectInto(this, view);



        return view;
    }


    @Override
    public void onRefresh() {
    }
}
