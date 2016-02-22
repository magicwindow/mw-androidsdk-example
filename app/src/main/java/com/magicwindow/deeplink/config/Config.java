/**
 *
 */
package com.magicwindow.deeplink.config;

/**
 * app所用到的常量
 *
 * @author Aaron
 */
public final class Config {

    public static float density; // 屏幕密度
    public static int height; // 屏幕高度
    public static int width; // 屏幕宽度

    /**
     * 魔窗demo app 存储目录/文件
     **/
    public static final String DIR = "/mwdemo";
    public static final String CACHE_DIR = DIR + "/images";
    public static final String LOG_DIR = DIR + "/logs";
    public static final String AVATAR_DIR = DIR + "/avatar/";

    /**
     * app默认地理位置信息
     **/
    public static final String CITY_NAME_DEFAULT = "上海";
    public static final String CITY_PINGYIN_DEFAULT = "shanghai"; // 默认城市
    public static final double LATITUDE_DEFAULT = 31.231706;
    public static final double LONGITUDE_DEFAULT = 121.472644;

    /** 存储app缓存的key **/

    /**
     * http请求响应
     **/
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAILURE = -1;
    public static final int RESULT_IOERROR = -2;
    public static final int NETWORK_ERROR = -3;


    public final static String MW_APPID = "MW_APPID";

    /**
     * 后配配置的所有魔窗位Key，我全部放在下面的String数组中。
     *
     */
    public static final String[] MWS = {
            "IY89LX3S",//1旅游-banner01
            "N1HI509T",//2旅游-banner02
            "FNH8F4L9",//3旅游-banner03
            "PZ2KP4EK",//4旅游-banner04
            "4V2SVA7L",//5旅游-list01
            "30H3ETI1",//6旅游-list02
            "11ALVPCG",//7旅游-list03
            "MPJNS0IU",//8旅游-list04
            "RLOJNTWO",//9旅游-list05
            "EDASC0YO",//10旅游-list06
            "8G1X9LK1",//11旅游-list07
            "HFTOO3DP",//12旅游-list08
            "YJMF5LRY",//13O2O-list01
            "IVMV6N2E",//14O2O-list02
            "YP7LYBYJ",//15O2O-list03
            "PCKRNFF2",//16O2O-list04
            "EVT17WBP",//17O2O-list05
            "GNOCMU9Y",//18图库-pic01
            "QSETZRGS",//19图库-pic02
            "NNDVD7QM",//20图库-pic03
            "O7KUXZW2",//21图库-pic04
            "OLVKILEL",//22图库-pic05
            "GYZAX2S3",//23图库-pic06
            "3UP88GCP",//24电商-banner01
            "0MG1OJGJ",//25电商-banner02
            "TKOZYCF0",//26电商-banner03
            "TAPU8B3Y",//27电商-banner04
            "XM1A5K31",//28电商-button01
            "WQS1D700",//29电商-button02
            "0FRBFRIR",//30电商-button03
            "6S1VJ96G",//31电商-button04
            "2C8CVEGJ",//32电商-button05
            "DLAZYGMT",//33电商-button06
            "6I83DA99",//34电商-button07
            "1FIMJGYQ",//35电商-button08
            "VMNLXHUR",//36电商-pic01
            "2LB72D77",//37电商-pic02
            "5JPNYN5C",//38电商-pic03
            "CTVGCCX4",//39电商-list01
            "QIST7PA1",//40电商-list02
            "TAPZHHIU",//41电商-list03
            "YOCXZMYJ",//42电商-list04
            "ZEY3KY1T",//43电商-list05
            "A8VNVSGG",//44电商-list06
            "MUDD1SSG",//45电商-list07
            "RJJ8WS93",//46电商-list08
            "0380O2V6",//47资讯-互联网-list01
            "VDCORPB1",//48资讯-互联网-list02
            "4GBDY0X2",//49资讯-互联网-list03
            "U0JHN1RZ",//50资讯-互联网-list04
            "NS76I781",//51资讯-互联网-list05
            "MXQQIZFG",//52资讯-互联网-list06
            "9HP08FU5",//53资讯-体育-list01
            "3BXJBEP5",//54资讯-体育-list02
            "BALLBIC1",//55资讯-体育-list03
            "98KVVY6Y",//56资讯-体育-list04
            "QTYF27YO",//57资讯-体育-list05
            "LR6VZ4KK",//58资讯-体育-list06
            "K5WSSGYO",//59资讯-娱乐-list01
            "TCI2WLWZ",//60资讯-娱乐-list02
            "L8F3KQVT",//61资讯-娱乐-list03
            "I4FRDG1U",//62资讯-娱乐-list04
            "0889TV7M",//63资讯-娱乐-list05
            "0CILEWMZ",//64资讯-娱乐-list06
            "I80655CR",//65O2O-banner01
            "K3EVKEUE",//66O2O-banner02
            "067Q8YCE",//67O2O-banner03
            "QH08INL1",//68O2O-banner04
            "IEES64MW",//69O2O-button01
            "JN8S7UWG",//70O2O-button02
            "ACMVG5SS",//71O2O-button03
            "XIP3B1MQ",//72O2O-button04
            "MQVNJ9EJ",//73O2O-pic01
            "Z00UQ8KZ",//74O2O-pic02
            "FS2Z29KG",//75O2O-pic03
            "TGHSGUI2",//76旅游-list09
            "2SRU2M3G",//77旅游-list10
            "1LO2ZIR8",//78旅游-list11
            "BQJMB17A",//79旅游-list12
            "3YEMBPHJ",//80旅游-list13
            "OQS1GKDS",//81旅游-list14
            "MQXL6PXP",//82旅游-list15
            "WP9SFIGY",//83旅游-list16
            "NX9WCEVJ",//84旅游-list17
            "LGGXYKGK",//85旅游-list18
            "UTRCHPIN",//86旅游-list19
            "RF8NF2K6",//87旅游-list20
            "S0XHWN18",//88旅游-list21
            "SOA3E4NP",//89旅游-list22
            "VHB6U0QW",//90旅游-list23
            "8L089IJ9",//91旅游-list24
            "UFSJ58DW",//92旅游-detail01
            "GI2DB3S2",//93设置-item01
            "SHSLEERI",//94设置-item02
            "ED4EM8ZG",//95设置-item03
            "ORDR80DB",//96设置-item04
            "S67K92GN",//97设置-item05
            "IH0R6VJG",//98设置-item06
            "SPZSFGKH"//99设置-item07
    };


    public static final String CUSTOM_COME_TO_BUY_ID = "R6QO83U1";    //点击购买
    public static final String CUSTOM_CLICK_TO_BUY_ID = "1WDAF2F7";    //进入购买页面
    public static final String CUSTOM_BUY_DONE_ID = "AHWMZMHL";    //购买完成

    public static final String MW_DIALOG = "45ELEGYU";    //dialog弹窗
    public static final String MW_DETAIL = "PZ2KP4EK";    //detail页面魔窗位
}
