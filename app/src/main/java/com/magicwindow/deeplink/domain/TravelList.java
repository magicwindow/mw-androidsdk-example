package com.magicwindow.deeplink.domain;

import java.util.List;

/**
 * @author aaron
 * @date 16/3/3
 */
public class TravelList {
    public List<TravelContent> contentList;
    public List<String> headList;

    public static class TravelContent {
        public String resource;
        public String desc;
        public String title;
        public String url;
    }

}
