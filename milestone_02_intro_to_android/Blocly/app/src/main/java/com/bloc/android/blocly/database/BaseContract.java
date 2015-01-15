package com.bloc.android.blocly.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Wayne on 1/11/2015.
 */
public class BaseContract {
    private static final Uri AUTHORITY_URI = Uri
            .parse("content://com.bloc.android.blocly.database.BaseProvider"); //this uri accesses the provider

    public BaseContract() {
    }

    public static abstract class FeedsEntry implements BaseColumns {
        public static final String TABLE = "rss_feeds"; //this is the name of table
        public static final String COLUMN_LINK = "link";//site URL
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FEED_URL = "feed_url";
        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE); //this is how the provider identifies our table
    }

    public static abstract class ItemsEntry implements BaseColumns {
        public static final String TABLE = "rss_items";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_GUID = "guid";
        public static final String COLUMN_PUB_DATE = "pub_date";
        public static final String COLUMN_ENCLOSURE = "enclosure";
        public static final String COLUMN_MIME_TYPE = "mime_type";
        public static final String COLUMN_RSS_FEED = "rss_feed";
        public static final String COLUMN_FAVORITE = "is_favorite";
        public static final String COLUMN_ARCHIVED = "is_archived";
        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE);
    }

}
