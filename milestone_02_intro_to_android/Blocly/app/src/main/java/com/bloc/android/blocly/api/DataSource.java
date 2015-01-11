package com.bloc.android.blocly.api;

import com.bloc.android.blocly.api.model.CompleteFeed;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.android.blocly.api.network.GetFeedsNetworkRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 12/24/2014.
 */
public class DataSource {
    private List<RssFeed> feeds;
    private List<RssItem> items;

    public DataSource() {
        feeds = new ArrayList<RssFeed>();
        items = new ArrayList<RssItem>();
        //final String currentFeedURL = feeds.get(0).getFeedUrl();
        // place request in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
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
