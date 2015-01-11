package com.bloc.android.blocly.api.model;

import java.util.ArrayList;

/**
 * Created by Wayne on 1/10/2015.
 */
public class CompleteFeed {
    RssFeed mRssFeed;
    ArrayList<RssItem> mRssItems;

    public RssFeed getRssFeed() {
        return mRssFeed;
    }

    public void setRssFeed(RssFeed rssFeed) {
        mRssFeed = rssFeed;
    }

    public ArrayList<RssItem> getRssItems() {
        return mRssItems;
    }

    public void setRssItems(ArrayList<RssItem> rssItems) {
        mRssItems = rssItems;
    }
}
