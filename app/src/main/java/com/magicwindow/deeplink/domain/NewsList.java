package com.magicwindow.deeplink.domain;

import java.util.List;

/**
 * @author aaron
 * @date 16/3/3
 */
public class NewsList {
    public List<NewsContent> internetList;
    public List<NewsContent> sportList;
    public List<NewsContent> entertainmentList;

    public static class NewsContent {
        public String resource;
        public String desc;
        public String title;
        public String url;
        public int mwKey;
    }

}
