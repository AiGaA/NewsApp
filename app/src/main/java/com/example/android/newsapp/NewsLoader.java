package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by AiGa on 2017.07.20..
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        return QueryUtils.fetchNewsData(url);
    }
}
