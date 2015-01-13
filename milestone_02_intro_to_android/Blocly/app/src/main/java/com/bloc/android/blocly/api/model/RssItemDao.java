package com.bloc.android.blocly.api.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import com.bloc.android.blocly.database.BaseContract;

/**
 * Created by Wayne on 1/11/2015.
 */
public class RssItemDao {
    private Context context;

    public RssItemDao(Context context) { //initializing context
        this.context = context;
    }

    public void insert(RssItem rssItem) { //now we create a insert for a feed object
        ContentValues values = new ContentValues();

        values.put(BaseContract.ItemsEntry.COLUMN_LINK, rssItem.getUrl());//we're linking name of tables to object represent
        values.put(BaseContract.ItemsEntry.COLUMN_TITLE, rssItem.getTitle());
        values.put(BaseContract.ItemsEntry.COLUMN_DESCRIPTION, rssItem.getDescription());
        values.put(BaseContract.ItemsEntry.COLUMN_GUID, rssItem.getGuid());
        values.put(BaseContract.ItemsEntry.COLUMN_PUB_DATE, rssItem.getDatePublished());
        values.put(BaseContract.ItemsEntry.COLUMN_ENCLOSURE, rssItem.getEnclosure());
        values.put(BaseContract.ItemsEntry.COLUMN_GUID, rssItem.getGuid());
        values.put(BaseContract.ItemsEntry.COLUMN_MIME_TYPE, rssItem.getMimeType());
        values.put(BaseContract.ItemsEntry.COLUMN_RSS_FEED, rssItem.getRssFeed());
        values.put(BaseContract.ItemsEntry.COLUMN_GUID, rssItem.getGuid());
        values.put(BaseContract.ItemsEntry.COLUMN_FAVORITE, rssItem.isFavorite());
        values.put(BaseContract.ItemsEntry.COLUMN_ARCHIVED, rssItem.isArchived());

        ContentUris.parseId(context.getContentResolver().insert(BaseContract.ItemsEntry.URI, values)); //uri identifies our table and put values in it
    }


}
