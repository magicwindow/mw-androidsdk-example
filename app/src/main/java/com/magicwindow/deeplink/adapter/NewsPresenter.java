package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.activity.WebViewActivity;
import com.magicwindow.deeplink.domain.NewsItem;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import cn.salesuite.saf.adapter.Presenter;
import cn.salesuite.saf.inject.annotation.InjectView;

/**
 * Created by Tony Shen on 16/2/2.
 */
public class NewsPresenter extends Presenter<NewsItem> {

    @InjectView(id= R.id.id_news_list_img)
    MWImageView listBg;

    @InjectView(id=R.id.id_news_list_title)
    TextView title;

    @InjectView(id=R.id.id_news_list_desc)
    TextView desc;

    public NewsPresenter(View view, Context context) {
        super(view);
        this.mContext = context;
    }

    @Override
    public void onBind(int position, final NewsItem item) {
        if (item != null) {
            listBg.setImageResource(item.imgRes);
            title.setText(item.title);
            desc.setText(item.desc);
            if (MarketingHelper.currentMarketing(mContext).isActive(item.mwKey)){
                listBg.bindEvent(item.mwKey);
                title.setText(MarketingHelper.currentMarketing(mContext).getTitle(item.mwKey));
                desc.setText(MarketingHelper.currentMarketing(mContext).getDescription(item.mwKey));
            }

            listBg.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.WEB_URL, item.url);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
