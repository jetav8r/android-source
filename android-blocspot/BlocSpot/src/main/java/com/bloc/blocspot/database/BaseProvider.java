package com.bloc.blocspot.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Wayne on 11/23/2014.
 */
public class BaseProvider extends ContentProvider {
    private BlocSpotHelper mBlocSpotHelper;

    @Override
    public boolean onCreate() {
        mBlocSpotHelper = new BlocSpotHelper(getContext());
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mBlocSpotHelper.getWritableDatabase(); //instance for write

        //execute delete
        int rows = db.delete(uri.getLastPathSegment(), selection, selectionArgs);

        //notify observers
        getContext().getContentResolver().notifyChange(uri, null);

        return rows;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mBlocSpotHelper.getWritableDatabase(); //the same thing

        //execute insert
        long id = db.insert(uri.getLastPathSegment(), null, values);

        //verify ok
        if (id == -1) {
            return null;
        }

        //notify observers
        getContext().getContentResolver().notifyChange(uri, null);

        //returning inserted row
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,	String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mBlocSpotHelper.getReadableDatabase(); // this is readable only

        //execute query
        Cursor cursor = db.query(uri.getLastPathSegment(), projection, selection, selectionArgs, null, null, sortOrder);

        //notify observers
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        SQLiteDatabase db = mBlocSpotHelper.getWritableDatabase();

        //execute update
        int rows = db.update(uri.getLastPathSegment(), values, selection, selectionArgs);

        //notify observers
        getContext().getContentResolver().notifyChange(uri, null);

        return rows;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
