package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AiGa on 2017.07.19..
 */

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public static final String LOG_TAG = NewsAdapter.class.getName();

    public NewsAdapter(Activity context, ArrayList<NewsItem> news) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        View newsList = convertView;

        if (newsList == null) {
            newsList = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(newsList);
            newsList.setTag(holder);
        } else {
            holder = (ViewHolder) newsList.getTag();
        }
        // Find the book at the given position in the list of books
        NewsItem currentNews = getItem(position);

        //Format the news section of the current news in that TextView
        holder.sectionView.setText(currentNews.getNewsSection());

        // Format the title of the current news in that TextView
        holder.titleView.setText(currentNews.getNewsTitle());


        // Format the date at the given position in the list of news
        holder.dateView.setText(currentNews.getDate());


        // Return the view
        return newsList;
    }

    static class ViewHolder {
        @BindView(R.id.newsSection)
        TextView sectionView;
        @BindView(R.id.newsTitle)
        TextView titleView;
        @BindView(R.id.newsDate)
        TextView dateView;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
