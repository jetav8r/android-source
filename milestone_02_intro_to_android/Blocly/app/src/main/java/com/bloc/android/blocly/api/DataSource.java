package com.bloc.android.blocly.api;

import android.database.sqlite.SQLiteDatabase;

import com.bloc.android.blocly.BloclyApplication;
import com.bloc.android.blocly.api.model.CompleteFeed;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.android.blocly.api.network.GetFeedsNetworkRequest;
import com.bloc.android.blocly.database.DatabaseOpenHelper;
import com.bloc.android.blocly.database.RssFeedTable;
import com.bloc.android.blocly.database.RssItemTable;
import com.bloc.blocly.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 12/24/2014.
 */
public class DataSource {
    private List<RssFeed> feeds;
    private List<RssItem> items;
    private DatabaseOpenHelper databaseOpenHelper;
    private RssFeedTable rssFeedTable;
    private RssItemTable rssItemTable;

    public DataSource() {
        rssFeedTable = new RssFeedTable();
        rssItemTable = new RssItemTable();
        // #6
        databaseOpenHelper = new DatabaseOpenHelper(BloclyApplication.getSharedInstance(),
                rssFeedTable, rssItemTable);
        feeds = new ArrayList<RssFeed>();
        items = new ArrayList<RssItem>();
        //final String currentFeedURL = feeds.get(0).getFeedUrl();
        // place request in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // We use the DEBUG property at #7 to decide whether or not to delete the existing database
                if (BuildConfig.DEBUG && false) {
                    BloclyApplication.getSharedInstance().deleteDatabase("blocly_db");
                }
                // open the database as a writable version of the database
                SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
                CompleteFeed completeFeed = new GetFeedsNetworkRequest("http://feeds.feedburner.com/androidcentral?format=xml").performRequest2();
                //new GetFeedsNetworkRequest("http://thebestmobile.com.br/feed/").performRequest();
                //new GetFeedsNetworkRequest(currentFeedURL).performRequest();
                createFakeData(completeFeed); // replace with feeds and items from network request
            }
        }).start();

    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }

    public List<RssItem> getItems() {
        return items;
    }

    public void createFakeData(CompleteFeed completeFeed) {

        //feeds.addAll(getRssFeeds from GetFeedsNetworkRequest.java)
        feeds.add(completeFeed.getRssFeed());
        for (int i = 0; i < completeFeed.getRssItems().size(); i++) {
            items.add(completeFeed.getRssItems().get(i));
        }
    }
}
