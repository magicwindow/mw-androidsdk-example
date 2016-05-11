package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.app.BaseFragment;

import cn.salesuite.saf.inject.Injector;

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
