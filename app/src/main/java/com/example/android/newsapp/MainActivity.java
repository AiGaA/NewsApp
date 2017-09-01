package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsItem>> {
    // Tag for LOG Message
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String API_BASE_URL = "http://content.guardianapis.com/search?q=tech&api-key=test";
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter adapter;
    private View loadingIndicator;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to Views
        final ListView newsListView = (ListView) findViewById(R.id.list);

        mEmptyView = (TextView) findViewById(R.id.empty_text_view);

        loadingIndicator = findViewById(R.id.progress_bar);

        // Set empty state view on the list view with books, when there is no data.
        newsListView.setEmptyView(mEmptyView);

        // Create a new adapter that takes an empty list of news as input
        adapter = new NewsAdapter(this, new ArrayList<NewsItem>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(adapter);

        //Create a ConnectivityManager and get the NetworkInfo from it
        final ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        //Create a boolean variable for the connectivity status
        final boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        //If the device is connected to the network
        if (isConnected) {
            Log.e(LOG_TAG, "This is called when there is an Internet connection.");

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            Log.e(LOG_TAG, "This is called when there is no Internet connection.");
            //Hide loading indicator so error will be visible
            loadingIndicator.setVisibility(View.GONE);
            //Show the empty state with no connection error message
            mEmptyView.setVisibility(View.VISIBLE);
            //Update empty state with no connection error message
            mEmptyView.setText(R.string.no_connection);
        }
        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                NewsItem currentNews = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        //Show the empty state with no connection error message
        mEmptyView.setVisibility(View.GONE);
        return new NewsLoader(this, API_BASE_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> news) {
        adapter.clear();
        //Hide loading indicator after content is loaded
        loadingIndicator.setVisibility(View.GONE);

        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        adapter.clear();
    }
}


