package com.bloc.blocnotes.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.bloc.blocnotes.Message;


import java.util.ArrayList;

/**
 * Created by Wayne on 9/27/2014.
 */


public class BlocNotesHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "blocnotes";
    //public static final int DATABASE_VERSION = 1;//increment version
    public static final int DATABASE_VERSION = 2;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String DEFAULT_VALUE = " DEFAULT ";

    public static final String CREATE_NOTEBOOKS = "CREATE TABLE " + BaseContract.NotebooksEntry.TABLE + " (" +
            BaseContract.NotebooksEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
            BaseContract.NotebooksEntry.NAME + TEXT_TYPE + COMMA_SEP +
            BaseContract.NotebooksEntry.DESCRIPTION + TEXT_TYPE + " );";


    public static final String CREATE_NOTES = "CREATE TABLE " + BaseContract.NotesEntry.TABLE + " (" +
                    BaseContract.NotesEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
                    BaseContract.NotesEntry.IMAGE_URL + TEXT_TYPE + COMMA_SEP + //this line added for image_url for version 2
                    BaseContract.NotesEntry.BODY + TEXT_TYPE + COMMA_SEP +
                    BaseContract.NotesEntry.REFERENCE + TEXT_TYPE + " );";

    public BlocNotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Message.message(context, "db constructor was called");
    }

    @Override
    public void onCreate(SQLiteDatabase mSqliteDatabase) {

        try {
            mSqliteDatabase.execSQL(CREATE_NOTEBOOKS);
            mSqliteDatabase.execSQL(CREATE_NOTES);
            //Message.message(context, "onCreate was called");
        } catch (SQLException e) {
            //Message.message(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion < 2){//upgrading to 2
            try{
                //we are adding a new column if the user already has the app installed. We do not
                //delete it, we upgrade it by Altering the table instead of destroying and recreating
                sqLiteDatabase.execSQL("ALTER TABLE " + BaseContract.NotesEntry.TABLE + " ADD COLUMN "
                        + BaseContract.NotesEntry.IMAGE_URL + TEXT_TYPE + ";");
                Log.e("Wayne","" + "upgraded");
            }catch (SQLException e){
                Log.e("Wayne","" + e.getMessage());
            }

        }
    }
}
