package com.bloc.android.blocly.api.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import com.bloc.android.blocly.database.BaseContract;

/**
 * Created by Wayne on 1/11/2015.
 */
public class RssFeedDao {
    private Context context;

    public RssFeedDao(Context context){ //initializing context
        this.context = context;
    }

    public void insert(RssFeed rssFeed){ //now we create a insert for a feed object
        ContentValues values = new ContentValues();

        values.put(BaseContract.FeedsEntry.COLUMN_LINK, rssFeed.getSiteUrl());//we're linking name of tables to object represent
        values.put(BaseContract.FeedsEntry.COLUMN_TITLE, rssFeed.getTitle());
        values.put(BaseContract.FeedsEntry.COLUMN_DESCRIPTION, rssFeed.getDescription());
        values.put(BaseContract.FeedsEntry.COLUMN_FEED_URL, rssFeed.getFeedUrl());

        ContentUris.parseId(context.getContentResolver().insert(BaseContract.FeedsEntry.URI, values)); //uri identifies our table and put values in it
    }

    public void update(RssFeed rssFeed) {
        ContentValues values = new ContentValues();
        values.put(BaseContract.FeedsEntry.COLUMN_LINK, rssFeed.getSiteUrl());
        values.put(BaseContract.FeedsEntry.COLUMN_TITLE, rssFeed.getTitle());
        values.put(BaseContract.FeedsEntry.COLUMN_DESCRIPTION, rssFeed.getDescription());
        values.put(BaseContract.FeedsEntry.COLUMN_FEED_URL, rssFeed.getFeedUrl());

        //to update a note we use a where clause and _ID to find it in the com.bloc.blocspot.database
        String selection = BaseContract.FeedsEntry._ID + " = ? ";
        String[] selectionArgs = new String[]{ String.valueOf(rssFeed.getId())};
        context.getContentResolver().update(BaseContract.FeedsEntry.URI, values, selection, selectionArgs);
    }

    public void delete(RssFeed rssFeed){
        rssFeed.setId("0");
        context.getContentResolver().delete(BaseContract.FeedsEntry.URI, BaseContract.FeedsEntry._ID + " = " + rssFeed.getId(), null);
    }
}
