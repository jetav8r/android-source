package com.bloc.android.blocly.database;

/**
 * Created by Wayne on 1/11/2015.
 */
public class RssFeedTable extends Table {

    private static final String COLUMN_LINK = "link";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FEED_URL = "feed_url";

    @Override
    public String getName() {
        return "rss_feeds";
    }

    @Override
    public String getCreateStatement() {
        // initializing ID as an INTEGER type, we specify that it is the unique Primary Key
        return "CREATE TABLE " + getName() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_LINK + " TEXT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_FEED_URL + " TEXT)";
    }
}
