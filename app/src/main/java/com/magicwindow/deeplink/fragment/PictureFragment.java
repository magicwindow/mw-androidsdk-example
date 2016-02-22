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
import com.magicwindow.deeplink.domain.PictureItem;

import java.util.ArrayList;
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
    List<PictureItem> mList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.magicwindow.deeplink.R.layout.fragment_picture, container, false);
        Injector.injectInto(this, view);

        initViews();
        return view;
    }

    private void initViews() {

        mList = new ArrayList<PictureItem>();

        PictureItem item1 = new PictureItem();
        item1.imgRes = com.magicwindow.deeplink.R.drawable.pic_01;
        item1.title = mContext.getString(com.magicwindow.deeplink.R.string.pic_list_title);
        item1.desc = mContext.getString(com.magicwindow.deeplink.R.string.pic_list_description);
        mList.add(item1);

        PictureItem item2 = new PictureItem();
        item2.imgRes = com.magicwindow.deeplink.R.drawable.pic_02;
        item2.title = "星际穿越";
        item2.desc = "科幻片";
        mList.add(item2);

        PictureItem item3 = new PictureItem();
        item3.imgRes = com.magicwindow.deeplink.R.drawable.pic_03;
        item3.title = "魔法黑森林";
        item3.desc = "奇幻片";
        mList.add(item3);


        PictureItem item4 = new PictureItem();
        item4.imgRes = com.magicwindow.deeplink.R.drawable.pic_04;
        item4.title = "消失的爱人";
        item4.desc = "悬疑片";
        mList.add(item4);


        PictureItem item5 = new PictureItem();
        item5.imgRes = com.magicwindow.deeplink.R.drawable.pic_05;
        item5.title = "布达佩斯大饭店";
        item5.desc = "喜剧片";
        mList.add(item5);


        PictureItem item6 = new PictureItem();
        item6.imgRes = com.magicwindow.deeplink.R.drawable.pic_06;
        item6.title = "模仿游戏";
        item6.desc = "惊悚片";
        mList.add(item6);
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
