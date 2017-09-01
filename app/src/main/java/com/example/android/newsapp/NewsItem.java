package com.example.android.newsapp;

/**
 * Created by AiGa on 2017.07.19..
 */

public class NewsItem {

    private String mNewsTitle;

    private String mNewsSection;

    private String mDate;

    private String mUrl;

    //public constructor
    public NewsItem(String section, String title, String date, String url) {
        mNewsSection = section;
        mNewsTitle = title;
        mDate = date;
        mUrl = url;
    }

    //getters
    public String getNewsSection() {
        return mNewsSection;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
