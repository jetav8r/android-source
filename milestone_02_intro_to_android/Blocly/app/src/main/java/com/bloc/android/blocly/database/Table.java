package com.bloc.android.blocly.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Wayne on 1/11/2015.
 */
public abstract class Table {

    // common column ID is provided to base classes
    protected static final String COLUMN_ID = "id";

    public abstract String getName();

    public abstract String getCreateStatement();

    // if we upgrade table, we do it here
    public void onUpgrade(SQLiteDatabase writableDatabase, int oldVersion, int newVersion) {
        // Nothing
    }
}
