package com.bloc.android.blocly.api.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bloc.android.blocly.database.BaseContract;

import java.util.ArrayList;

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




    public ArrayList<RssItem> getAllItems() {
        Cursor cursor = context.getContentResolver().query(BaseContract.ItemsEntry.URI,
                null,
                null,//all
                null,//all
                BaseContract.ItemsEntry.COLUMN_TITLE);//sort by title

        ArrayList<RssItem> list = new ArrayList<RssItem>();

        if (cursor.moveToFirst()) {
            do {
                RssItem rssItem = new RssItem();
                rssItem.setId(cursor.getString(cursor.getColumnIndex(BaseContract.ItemsEntry._ID)));//this id is generated automatically,
                rssItem.setGuid(cursor.getString(cursor.getColumnIndex(BaseContract.ItemsEntry.COLUMN_GUID)));
                rssItem.setTitle(cursor.getString(cursor.getColumnIndex(BaseContract.ItemsEntry.COLUMN_TITLE)));
                rssItem.setDescription(cursor.getString(cursor.getColumnIndex(BaseContract.ItemsEntry.COLUMN_DESCRIPTION)));
                rssItem.setUrl(cursor.getString(cursor.getColumnIndex(BaseContract.ItemsEntry.COLUMN_LINK)));
                list.add(rssItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

        public ArrayList<RssItem> getTenItems(){
            String sortOrder = BaseContract.ItemsEntry.COLUMN_PUB_DATE;
            Cursor cursor10 = context.getContentResolver().query(BaseContract.ItemsEntry.URI,
                    null,
                    null,//all
                    null,//all
                    sortOrder + " LIMIT 10");


            ArrayList<RssItem> list10 = new ArrayList<RssItem>();

            if (cursor10.moveToFirst()) {
                do {
                    RssItem rssItem = new RssItem();
                    rssItem.setId(cursor10.getString(cursor10.getColumnIndex(BaseContract.ItemsEntry._ID)));//this id is generated automatically,
                    rssItem.setGuid(cursor10.getString(cursor10.getColumnIndex(BaseContract.ItemsEntry.COLUMN_GUID)));
                    rssItem.setTitle(cursor10.getString(cursor10.getColumnIndex(BaseContract.ItemsEntry.COLUMN_TITLE)));
                    rssItem.setDescription(cursor10.getString(cursor10.getColumnIndex(BaseContract.ItemsEntry.COLUMN_DESCRIPTION)));
                    rssItem.setUrl(cursor10.getString(cursor10.getColumnIndex(BaseContract.ItemsEntry.COLUMN_LINK)));
                    rssItem.setDatePublished(cursor10.getString(cursor10.getColumnIndex(BaseContract.ItemsEntry.COLUMN_PUB_DATE)));
                    list10.add(rssItem);
                    //or we limit here or...
                } while (cursor10.moveToNext());
            }
            cursor10.close();
            return list10;

}


}
