package com.bloc.android.blocly.api;

import android.content.Context;

import com.bloc.android.blocly.api.model.CompleteFeed;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssFeedDao;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.android.blocly.api.model.RssItemDao;
import com.bloc.android.blocly.api.network.GetFeedsNetworkRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 12/24/2014.
 */
public class DataSource {
    private List<RssFeed> feeds;
    private List<RssItem> items;
    private Context mContext;
   /* private DatabaseOpenHelper databaseOpenHelper;
    private RssFeedTable rssFeedTable;
    private RssItemTable rssItemTable;*/

    public DataSource(Context context) {
        mContext = context;
        /*rssFeedTable = new RssFeedTable();
        rssItemTable = new RssItemTable();
        // #6
        databaseOpenHelper = new DatabaseOpenHelper(BloclyApplication.getSharedInstance(),
                rssFeedTable, rssItemTable);*/
        feeds = new ArrayList<>();
        items = new ArrayList<>();
        //final String currentFeedURL = feeds.get(0).getFeedUrl();
        // place request in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // We use the DEBUG property at #7 to decide whether or not to delete the existing database
                /*if (BuildConfig.DEBUG && false) {
                    BloclyApplication.getSharedInstance().deleteDatabase("blocly_db");
                }*/
                // open the database as a writable version of the database
                //SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
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
        RssFeedDao rssFeedDao = new RssFeedDao(mContext);
        RssItemDao rssItemDao = new RssItemDao(mContext);

        //feeds.addAll(getRssFeeds from GetFeedsNetworkRequest.java)
        feeds.add(completeFeed.getRssFeed());
        try {
            rssFeedDao.insert(completeFeed.getRssFeed());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < completeFeed.getRssItems().size(); i++) {
            items.add(completeFeed.getRssItems().get(i));
            try {
                rssItemDao.insert(completeFeed.getRssItems().get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
