package com.bloc.blocnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Wayne on 9/26/2014.
 */
public class BlocNotesDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BlocNotesDb";
    private static final String SQL_CREATE_NOTEBOOKS =
            "CREATE TABLE Notebooks (" +
                    "_id INTEGER PRIMARY KEY," +
                    "nb_identifier TEXT," +
                    "name TEXT," +
                    "description TEXT" +
                    " )";

    private static final String SQL_CREATE_NOTES =
            "CREATE TABLE Notes (" +
                    "_id INTEGER PRIMARY KEY," +
                    "note_identifier TEXT," +
                    "body TEXT," +
                    "reference TEXT" +
                    " )";


    public BlocNotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("Wayne", "constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create Tables

        try {
            sqLiteDatabase.execSQL(SQL_CREATE_NOTES);
            Log.d("Wayne", "notes table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_NOTEBOOKS);
            Log.d("Wayne", "notebooks table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ContentValues values = new ContentValues();

        values.put("nb_identifier", "Uncategorized");
        values.put("name", "Default");
        values.put("description", "Default Notebook");
        long notebookId = sqLiteDatabase.insert("Notebooks", null, values);

        values.clear();
        values.put("note_identifier", "First");
        values.put("body", "Editable Text");
        values.put("reference", "Editable Text");
        long noteId = sqLiteDatabase.insert("Notes", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" +DATABASE_NAME);
            onCreate(sqLiteDatabase);
            Log.d("Wayne", "database was updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
