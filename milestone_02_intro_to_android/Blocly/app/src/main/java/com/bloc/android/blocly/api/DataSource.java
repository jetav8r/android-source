package com.bloc.android.blocly.api;

import com.bloc.android.blocly.BloclyApplication;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.android.blocly.api.network.GetFeedsNetworkRequest;
import com.bloc.blocly.R;

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
        createFakeData();
        // place request in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //new GetFeedsNetworkRequest("http://feeds.feedburner.com/androidcentral?format=xml").performRequest();
                new GetFeedsNetworkRequest("http://thebestmobile.com.br/feed/").performRequest();
            }
        }).start();
    }

    public List<RssFeed> getFeeds() {
        return feeds;
    }

    public List<RssItem> getItems() {
        return items;
    }

    void createFakeData() {
        feeds.add(new RssFeed("My Favorite Feed",
                "This feed is just incredible, I can't even begin to tell you…",
                "http://favoritefeed.net", "http://feeds.feedburner.com/favorite_feed?format=xml"));
        for (int i = 0; i < 10; i++) {
            items.add(new RssItem(String.valueOf(i),
                    BloclyApplication.getSharedInstance().getString(R.string.placeholder_headline) + " " + i,
                    BloclyApplication.getSharedInstance().getString(R.string.placeholder_content),
                    "http://favoritefeed.net?story_id=an-incredible-news-story",
                    //null,   //we can substitute null for testing if no image Url
                    "http://rs1img.memecdn.com/silly-dog_o_511213.jpg",
                    System.currentTimeMillis(),
                    false, false, false));
        }
    }
}
