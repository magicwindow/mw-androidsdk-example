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
import com.magicwindow.deeplink.config.Config;
import com.magicwindow.deeplink.domain.NewsItem;
import com.magicwindow.deeplink.ui.DividerItemDecoration;
import com.magicwindow.deeplink.ui.RefreshLayout;

import java.util.ArrayList;

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

    @InjectView(id = com.magicwindow.deeplink.R.id.film_detail_container)
    RefreshLayout swipeContainer;

    @InjectView(id = com.magicwindow.deeplink.R.id.news_detail_list)
    RecyclerView recyclerView;

    SAFRecycleAdapter adapter;

    private static String STYLE = "style";
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
        ArrayList<NewsItem> mList = new ArrayList<NewsItem>();
        NewsItem item1 = new NewsItem();
        NewsItem item2 = new NewsItem();
        NewsItem item3 = new NewsItem();
        NewsItem item4 = new NewsItem();
        NewsItem item5 = new NewsItem();
        NewsItem item6 = new NewsItem();

        switch (STYLE_VALUE) {
            default:
            case 0:
                mList.clear();
                item1.imgRes = com.magicwindow.deeplink.R.drawable.news011;
                item1.title = "国产手机出海赴美该如何应对专利围剿？";
                item1.desc = "小米、华为等国产手机进军美国市场，面临的最大诉讼压力未必是来自同业竞争对手，而更多是NPE机构，那该如何应对呢？";
                item1.date = "June 15";
                item1.mwKey = Config.MWS[46];
                item1.url = "http://m.cyzone.cn/a/20151112/283572.html";
                mList.add(item1);

                item2.imgRes = com.magicwindow.deeplink.R.drawable.news012;
                item2.title = "O2O创业的死亡率为什么那么高？";
                item2.desc = "创业门槛的不断下探，让很多人为创业的春天欢呼，不过雪莱的名言反过来也成立，春天来了，冬天还会远吗？";
                item2.date = "January 30";
                item2.mwKey = Config.MWS[47];
                item2.url="http://m.cyzone.cn/a/20151112/283572.html";
                mList.add(item2);

                item3.imgRes = com.magicwindow.deeplink.R.drawable.news013;
                item3.title = "银联、腾讯和阿里的支付战争悄然重启";
                item3.desc = "在支付宝和微信支付涉足线下之前，其一直是银联一家的地盘——依靠着商户与消费者之间的连接和一份7：2：1（发卡行、收单行、银联）的分成协议，躺着赚钱。";
                item3.date = "February 10";
                item3.mwKey = Config.MWS[48];
                item3.url="http://news.mbalib.com/story/100384";
                mList.add(item3);

                item4.imgRes = com.magicwindow.deeplink.R.drawable.news014;
                item4.title = "社群经济是自媒体大号的未来吗？";
                item4.desc = "社群，是运营驱动的模式，而且对运营的投入，远远超过作为一个媒体对运营的投入。 所有人，都尚在探索。";
                item4.date = "June 2";
                item4.mwKey = Config.MWS[49];
                item4.url="http://xw.qq.com/cul/20151208049795";
                mList.add(item4);

                item5.imgRes = com.magicwindow.deeplink.R.drawable.news015;
                item5.title = "互联网＋时代， 如何规制分享经济？";
                item5.desc = "“我为人人，人人为我。”大仲马（法）在《三个火枪手》的这句话可谓点出了分享经济的真谛。";
                item5.date = "February 10";
                item5.mwKey = Config.MWS[50];
                item5.url="http://m.opinion.caixin.com/m/2015-12-08/100883319.html";
                mList.add(item5);

                item6.imgRes = com.magicwindow.deeplink.R.drawable.news016;
                item6.title = "中联通4G+对用户补贴450亿，你真的在乎吗？";
                item6.desc = "中联通4G+策略出炉，此前中电信4G+、中移动4G+至少进行了100多天。中联通4G+与前两者最大亮点是对用户450亿补贴，产业链和用户是否买账？";
                item6.date = "December 23";
                item6.mwKey = Config.MWS[51];
                item6.url="http://m.sohu.com/n/430422488/?_trans_=000115_3w";
                mList.add(item6);
                adapter = new SAFRecycleAdapter(mList);
                break;

            case 1:
                mList.clear();
                item1.imgRes = com.magicwindow.deeplink.R.drawable.news021;
                item1.title = "科比加内特！NBA两尊傲骨 再嗅一嗅熟悉的硝烟。";
                item1.desc = "谁知老卧江湖上，犹枕当年虎骷髅。科比和加内特，NBA会永远记住他们的故事。";
                item1.date = "June 15";
                item1.mwKey = Config.MWS[52];
                item1.url="http://money.163.com/15/1211/00/BAH15M0I00253B0H.html";
                mList.add(item1);

                item2.imgRes = com.magicwindow.deeplink.R.drawable.news022;
                item2.title = "勇士跨季27连胜平13热火 33连胜？就是对骑士!";
                item2.desc = "勇士跨赛季27连胜，已经追平了2012-13赛季的热火。下一个目标就是NBA历史最长的33连胜。";
                item2.date = "January 30";
                item2.mwKey = Config.MWS[53];
                item2.url="http://sports.sina.cn/nba/warriors/2015-12-09/detail-ifxmnurf8474922.d.html?from=wap";
                mList.add(item2);

                item3.imgRes = com.magicwindow.deeplink.R.drawable.news023;
                item3.title = "欧冠夺冠赔率：巴萨独占榜首 英超3强被看衰";
                item3.desc = "16强产生后，欧冠夺冠赔率榜更新，巴萨1赔3.25独占第一，拜仁、皇马分居二、三位，英超3强则全部被看衰。";
                item3.date = "February 10";
                item3.mwKey = Config.MWS[54];
                item3.url="http://sports.sina.com.cn/l/2015-12-10/doc-ifxmisxu6382943.shtml?cre=sinapc&mod=g&loc=18&r=u&rfunc=5";
                mList.add(item3);

                item4.imgRes = com.magicwindow.deeplink.R.drawable.news024;
                item4.title = "神奇反转！吉鲁帽子戏法 阿森纳3-0晋级";
                item4.desc = "2015/16欧冠F组第6比赛日一场焦点战在卡莱斯卡基斯球场展开争夺，阿森纳客场3比0完胜奥林匹亚科斯，吉鲁帽子戏法";
                item4.date = "June 2";
                item4.mwKey = Config.MWS[55];
                item4.url="http://sports.sina.com.cn/g/pl/2015-12-10/doc-ifxmpnqm2996245.shtml";
                mList.add(item4);

                item5.imgRes = com.magicwindow.deeplink.R.drawable.news025;
                item5.title = "孙悦罚球绝杀！北京加时胜广东终结3连败";
                item5.desc = "CBA第15轮全面开战，京粤大战激情上演。此前三连败的卫冕冠军北京队回到了万事达通过加时战以106-104战胜了广东队，孙悦上演罚球绝杀。";
                item5.date = "February 10";
                item5.mwKey = Config.MWS[56];
                item5.url="http://sports.sina.com.cn/cba/2015-12-09/doc-ifxmpnqm2984210.shtml";
                mList.add(item5);

                item6.imgRes = com.magicwindow.deeplink.R.drawable.news026;
                item6.title = "恒大提交世俱杯23人大名单：6外援报名 没带雷内";
                item6.desc = "2015年世界俱乐部杯比赛将在12月中旬举行，日前亚洲冠军广州恒大方面提交了最终的23人参赛名单，恒大报上了6位外援，雷内没有在参赛名单之中。";
                item6.date = "December 23";
                item6.mwKey = Config.MWS[57];
                item6.url="http://sports.sina.com.cn/china/j/2015-11-29/doc-ifxmazmy2240003.shtml";
                mList.add(item6);
                adapter = new SAFRecycleAdapter(mList);
                break;

            case 2:
                mList.clear();
                item1.imgRes = com.magicwindow.deeplink.R.drawable.news031;
                item1.title = "内地电影票房将破400亿 票房前十是哪几部？";
                item1.desc = "截至目前，票房前三甲分别是《捉妖记》《速度与激情7》以及《港囧》。在票房前十的影片中，国产影片占到6部，在这6部影片中喜剧片占到一半。";
                item1.date = "June 15";
                item1.mwKey = Config.MWS[58];
                item1.url="http://t.m.china.com.cn/convert/c_KfO24i.html";
                mList.add(item1);

                item2.imgRes = com.magicwindow.deeplink.R.drawable.news032;
                item2.title = "陈伟霆李治廷为什么拼不过鹿晗、吴亦凡？";
                item2.desc = "因为耳机放着李治廷的尼古拉，想着李治廷的颜值，演技和学历，为什么他就没火起来呢？反观棒子国回来的鹿晗、吴亦凡基本都有一比一的电影圈资源。真的让人很费解。";
                item2.date = "January 30";
                item2.mwKey = Config.MWS[59];
                item2.url="http://tech.xinmin.cn/internet/2015/12/10/29084538.html";
                mList.add(item2);

                item3.imgRes = com.magicwindow.deeplink.R.drawable.news033;
                item3.title = "主持人欧弟升级当爸爸 妻子郑云灿产下爱女。";
                item3.desc = "湖南卫视工作人员“潇湘卧龙”也在微博发文向欧弟道贺，证实了欧弟的妻子郑云灿顺利产下爱女。";
                item3.date = "February 10";
                item3.mwKey = Config.MWS[60];
                item3.url="http://www.sc.xinhuanet.com/content/2015-12/10/c_1117413845.htm";
                mList.add(item3);

                item4.imgRes = com.magicwindow.deeplink.R.drawable.news034;
                item4.title = "《芈月传》高云翔：导演真坏！勾出我全部狂野。";
                item4.desc = "也许是习惯了高云翔给人的最初印象——特别帅气的阳光大男孩，所以当他在《芈月传》中初登场，嘶吼着冲出，一路策马扬鞭狂追芈月时，一时间居然没认出来。";
                item4.date = "June 2";
                item4.mwKey = Config.MWS[61];
                item4.url="http://et.21cn.com/webfocus/a/2015/1209/17/30358613.shtml";
                mList.add(item4);

                item5.imgRes = com.magicwindow.deeplink.R.drawable.news035;
                item5.title = "陈晓自曝已经与陈妍希同居。";
                item5.desc = "新房正在进行装修，称新房正在装修；还透露爱吃补肾食物山药和秋葵。";
                item5.date = "February 10";
                item5.mwKey = Config.MWS[62];
                item5.url="http://fun.youth.cn/2015/1214/3185403.shtml";
                mList.add(item5);

                item6.imgRes = com.magicwindow.deeplink.R.drawable.news036;
                item6.title = "《万万没想到》上海首映 韩寒被曝遭保安打。";
                item6.desc = "将于贺岁档上映的奇幻喜剧《万万没想到》在沪首映，导演叫兽易小星、韩寒与主演杨子姗、白客、陈柏霖等到场。现场，韩寒被曝拍戏过程中，为抱孔连顺遭保安痛打。";
                item6.date = "December 23";
                item6.mwKey = Config.MWS[63];
                item6.url="http://ent.qq.com/a/20151209/069249.htm";
                mList.add(item6);
                adapter = new SAFRecycleAdapter(mList);
                break;

        }
        adapter.createPresenter(new Func2<ViewGroup, Integer, Presenter>(){

            @Override
            public Presenter call(ViewGroup parent, Integer integer) {

                return new NewsPresenter(LayoutInflater.from(mContext).inflate(R.layout.cell_news, parent, false),mContext);
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
