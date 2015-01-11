package com.bloc.android.blocly.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wayne on 1/11/2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    // database will be named blocly_db. The name is kept as a static final variable
    private static final String NAME = "blocly_db";
    // version will remain 1 until we choose to update the database in a later releas
    private static final int VERSION = 1;

    private Table[] tables;

    public DatabaseOpenHelper(Context context, Table... tables) {
        // pass both the version and name of the database to the super constructor
        super(context, NAME, null, VERSION);
        this.tables = tables;
    }

    // iterate over all Table objects and execute their Create statements
    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Table table : tables) {
            db.execSQL(table.getCreateStatement());
        }
    }

    // iterate over our Table objects and invoke the appropriate upgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table table : tables) {
            table.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
