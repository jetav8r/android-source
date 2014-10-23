package com.bloc.blocnotes;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Wayne on 10/2/2014.
 */
    public class DbLauncher extends Application {

    BlocNotesHelper blocNotesHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        blocNotesHelper = new BlocNotesHelper(this);
        SQLiteDatabase sqLiteDatabase = blocNotesHelper.getWritableDatabase();
    }
}

