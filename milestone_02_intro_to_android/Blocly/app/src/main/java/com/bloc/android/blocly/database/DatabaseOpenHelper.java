package com.bloc.android.blocly.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wayne on 1/11/2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private Context context;
    // database will be named blocly_db. The name is kept as a static final variable
    private static final String DATABASE_NAME = "blocly_db";
    // version will remain 1 until we choose to update the database in a later release
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String DEFAULT_VALUE = " DEFAULT ";

    public static final String CREATE_FEEDS = "CREATE TABLE " + BaseContract.FeedsEntry.TABLE + " (" +
            BaseContract.FeedsEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
            BaseContract.FeedsEntry.COLUMN_LINK + TEXT_TYPE + " UNIQUE  " + COMMA_SEP +
            BaseContract.FeedsEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
            BaseContract.FeedsEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            BaseContract.FeedsEntry.COLUMN_FEED_URL + TEXT_TYPE +  " );";

    public static final String CREATE_ITEMS = "CREATE TABLE " + BaseContract.ItemsEntry.TABLE + " (" +
            BaseContract.ItemsEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_LINK + TEXT_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_GUID + TEXT_TYPE + " UNIQUE  " + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_PUB_DATE + TEXT_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_ENCLOSURE + TEXT_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_MIME_TYPE + TEXT_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_RSS_FEED + INTEGER_TYPE + COMMA_SEP + //store the row identifier
            // of the RSS feed entry to which each item belongs. This relationship will allow us to filter RSS items by feed.
            BaseContract.ItemsEntry.COLUMN_ARCHIVED + INTEGER_TYPE + COMMA_SEP +
            BaseContract.ItemsEntry.COLUMN_FAVORITE + INTEGER_TYPE + " );";


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Message.message(context, "db constructor was called");
    }

    @Override
    public void onCreate(SQLiteDatabase mSqliteDatabase) {


            mSqliteDatabase.execSQL(CREATE_FEEDS);
            mSqliteDatabase.execSQL(CREATE_ITEMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP_TABLE_IF_EXISTS" + CREATE_FEEDS);
        sqLiteDatabase.execSQL("DROP_TABLE_IF_EXISTS" + CREATE_ITEMS);

        onCreate(sqLiteDatabase);
    }
}
