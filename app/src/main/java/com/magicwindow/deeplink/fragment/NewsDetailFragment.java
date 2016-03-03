package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.adapter.NewsPresenter;
import com.magicwindow.deeplink.app.BaseFragment;
import com.magicwindow.deeplink.domain.NewsList;
import com.magicwindow.deeplink.prefs.AppPrefs;
import com.magicwindow.deeplink.ui.DividerItemDecoration;
import com.magicwindow.deeplink.ui.RefreshLayout;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.adapter.SAFRecycleAdapter;
import cn.salesuite.saf.inject.Injector;
import cn.salesuite.saf.inject.annotation.InjectView;
import cn.salesuite.saf.log.L;
import cn.salesuite.saf.utils.SAFUtils;
import rx.functions.Func2;

/**
 * Created by Tony Shen on 15/11/25.
 */
public class NewsDetailFragment extends BaseFragment {

    private static String STYLE = "style";
    @InjectView(id = com.magicwindow.deeplink.R.id.film_detail_container)
    RefreshLayout swipeContainer;
    @InjectView(id = com.magicwindow.deeplink.R.id.news_detail_list)
    RecyclerView recyclerView;
    SAFRecycleAdapter adapter;
    int STYLE_VALUE = 0;

    public static NewsDetailFragment newInstance(int style) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putInt(STYLE, style);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            L.e("NewsDetailFragment visible");
//            TrackAgent.currentEvent().onPageStart("主 页");

        } else {
            L.e("NewsDetailFragment invisible");
//            TrackAgent.currentEvent().onPageEnd("主 页");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.magicwindow.deeplink.R.layout.fragment_news_detail, container, false);
        Injector.injectInto(this, view);

        STYLE_VALUE = getArguments().getInt(STYLE, 0);

        initViews();

        return view;
    }

    private void initViews() {
//        NewsItem item1 = new NewsItem();
//        NewsItem item2 = new NewsItem();
//        NewsItem item3 = new NewsItem();
//        NewsItem item4 = new NewsItem();
//        NewsItem item5 = new NewsItem();
//        NewsItem item6 = new NewsItem();
        NewsList list = AppPrefs.get(mContext).getNewsList();
        //新闻页面第一个魔窗位死Config.MWS[46]
        list.internetList.get(0).mwKey = 46;
        list.sportList.get(0).mwKey = 46 + list.internetList.size();
        list.entertainmentList.get(0).mwKey = 46 + list.internetList.size() + list.sportList.size();

        switch (STYLE_VALUE) {
            default:
            case 0:
                adapter = new SAFRecycleAdapter(list.internetList);
                break;

            case 1:

                adapter = new SAFRecycleAdapter(list.sportList);
                break;

            case 2:
                adapter = new SAFRecycleAdapter(list.entertainmentList);
                break;

        }
        adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>() {

            @Override
            public Presenter call(ViewGroup parent, Integer integer) {

                return new NewsPresenter(LayoutInflater.from(mContext).inflate(R.layout.cell_news, parent, false), mContext);
            }
        });


        recyclerView.setAdapter(adapter);
        LinearLayoutManager mgr = new LinearLayoutManager(mContext);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mgr);

        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        swipeContainer.setMoreData(false);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!SAFUtils.checkNetworkStatus(app)) {
                            toast(com.magicwindow.deeplink.R.string.network_error);
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
