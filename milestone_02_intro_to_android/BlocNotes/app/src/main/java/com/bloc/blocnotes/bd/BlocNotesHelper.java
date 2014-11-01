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
    //public static final String TABLE1_NAME = "notebooks";
    //public static final String TABLE2_NAME = "notes";
    public static final int DATABASE_VERSION = 1;
    //public static final String UID = "_id";
    //public static final String name = "name";
    //public static final String body = "body";
    //public static final String description = "description";
    //public static final String reference = "notebook_reference";
    //public static final String DROP_TABLE_1 = "DROP TABLE IF EXISTS " + TABLE1_NAME + "";
    //public static final String DROP_TABLE_2 = "DROP TABLE IF EXISTS " + TABLE2_NAME + "";

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
                    BaseContract.NotesEntry.BODY + TEXT_TYPE + COMMA_SEP +
                    BaseContract.NotesEntry.REFERENCE + TEXT_TYPE + " );";

    public BlocNotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Message.message(context, "db constructor was called");
    }

    /*
    public Cursor getAllNotebooks(){
        String[] columns = {UID, name, description};
        return this.getWritableDatabase().query(TABLE1_NAME,columns,null,null,null,null,null);
    }
*/

    //public void insertNewNotebook(String newNotebookName){
        //String mNewNotebookName=newNotebookName;
        //SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Message.message(context," " + newNotebookName);
        //ContentValues values = new ContentValues();
        //values.put(name, mNewNotebookName);
        //values.put(description, "New Notebook");
        //long id = sqLiteDatabase.insert("Notebooks", description, values);
        //values.clear();
    //}

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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        try {
            //sqLiteDatabase.execSQL(DROP_TABLE_1); //no the best aproach, but see it later
            //sqLiteDatabase.execSQL(DROP_TABLE_2);
            Message.message(context, "onUpgrade was called");
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            Message.message(context, "" + e);
        }
    }
}
