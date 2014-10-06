package com.bloc.blocnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Wayne on 9/27/2014.
 */
public class BlocNotesHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "blocnotes";
    private static final String TABLE1_NAME = "notebooks";
    private static final String TABLE2_NAME = "notes";
    private static final int DATABASE_VERSION = 3;
    private static final String UID = "_id";
    private static final String name = "name ";
    private static final String body = "body";
    private static final String description = "description";
    private static final String reference = "notebook_reference";
    private static final String DROP_TABLE_1 = "DROP TABLE IF EXISTS " + TABLE1_NAME + "";
    private static final String DROP_TABLE_2 = "DROP TABLE IF EXISTS " + TABLE2_NAME + "";

    private static final String CREATE_NOTEBOOKS =
            "CREATE TABLE " + TABLE1_NAME +
                    "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + name + " VARCHAR(255)," +
                    description + " VARCHAR(255)" + " )";
    private static final String CREATE_NOTES =
            "CREATE TABLE " + TABLE2_NAME +
                    "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + body + " VARCHAR(255)," +
                    reference + " VARCHAR(255)" + " )";

    public BlocNotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Message.message(context, "constructor was called");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_NOTEBOOKS);
            sqLiteDatabase.execSQL(CREATE_NOTES);
            Message.message(context, "onCreate was called");
        } catch (SQLException e) {
            Message.message(context, "" + e);
        }
        ContentValues values = new ContentValues();

        values.put(UID, "1");
        values.put(name, "Uncategorized");
        values.put(description, "Default Notebook");
        sqLiteDatabase.insert("Notebooks", null, values);

        values.clear();
        values.put(UID, "1");
        values.put(body, "Begin typing note here");
        values.put(reference, "Uncategorized");
        sqLiteDatabase.insert("Notes", null, values);

        //sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE_1);
            sqLiteDatabase.execSQL(DROP_TABLE_2);
            Message.message(context, "onUpgrade was called");
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            Message.message(context, "" + e);
        }
    }
}
