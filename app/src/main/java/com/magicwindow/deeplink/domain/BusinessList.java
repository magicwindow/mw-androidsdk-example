package com.magicwindow.deeplink.domain;

import java.util.List;

/**
 * @author aaron
 * @date 16/3/3
 */
public class BusinessList {
    public List<String> middleList;
    public List<String> headList;
    public List<BusinessContent> contentList;

    public static class BusinessContent {
        public String resource;
        public String title;
    }

}
