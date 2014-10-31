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
            /*ContentValues values = new ContentValues();

            values.put(BaseContract.NotebooksEntry._ID,"1");
            values.put(BaseContract.NotebooksEntry.NAME,"Uncategorized");
            values.put(BaseContract.NotebooksEntry.DESCRIPTION,"Default Notebook");
            mSqliteDatabase.insert("Notebooks", null, values);
            values.clear();

            values.put(BaseContract.NotebooksEntry._ID,"2");
            values.put(BaseContract.NotebooksEntry.NAME,"Dreams");
            values.put(BaseContract.NotebooksEntry.DESCRIPTION,"Dreams Notebook");
            mSqliteDatabase.insert("Notebooks", null, values);
            values.clear();

            values.put(BaseContract.NotebooksEntry._ID,"3");
            values.put(BaseContract.NotebooksEntry.NAME,"To Do");
            values.put(BaseContract.NotebooksEntry.DESCRIPTION,"To Do List");
            mSqliteDatabase.insert("Notebooks", null, values);
            values.clear();

            values.put(BaseContract.NotebooksEntry._ID,"4");
            values.put(BaseContract.NotebooksEntry.NAME,"Goals");
            values.put(BaseContract.NotebooksEntry.DESCRIPTION,"Long Term Goals");
            mSqliteDatabase.insert("Notebooks", null, values);
            values.clear();

            values.put(BaseContract.NotebooksEntry._ID,"5");
            values.put(BaseContract.NotebooksEntry.NAME,"Grocery List");
            values.put(BaseContract.NotebooksEntry.DESCRIPTION,"Grocery List");
            mSqliteDatabase.insert("Notebooks", null, values);
            values.clear();

            values.put(BaseContract.NotesEntry._ID,"1");
            values.put(BaseContract.NotesEntry.BODY,"Begin typing note here");
            values.put(BaseContract.NotesEntry.REFERENCE,"Uncategorized");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"2");
            values.put(BaseContract.NotesEntry.BODY,"I had a dream that I drank the worlds' biggest margarita.  When woke up I was hugging the toilet");
            values.put(BaseContract.NotesEntry.REFERENCE,"Dreams");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"3");
            values.put(BaseContract.NotesEntry.BODY,"My dream last night was that I had 45 dogs in my house!");
            values.put(BaseContract.NotesEntry.REFERENCE,"Dreams");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"4");
            values.put(BaseContract.NotesEntry.BODY,"I had a dream that I was dreaming");
            values.put(BaseContract.NotesEntry.REFERENCE,"Dreams");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"5");
            values.put(BaseContract.NotesEntry.BODY,"Find grant writer for dog stuff");
            values.put(BaseContract.NotesEntry.REFERENCE,"To Do");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"6");
            values.put(BaseContract.NotesEntry.BODY,"Finish fixing shifter-karts");
            values.put(BaseContract.NotesEntry.REFERENCE,"To Do");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"7");
            values.put(BaseContract.NotesEntry.BODY,"Build Sanctuary for rescued dogs");
            values.put(BaseContract.NotesEntry.REFERENCE,"Goals");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"8");
            values.put(BaseContract.NotesEntry.BODY,"Become good at Android programming");
            values.put(BaseContract.NotesEntry.REFERENCE,"Goals");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"9");
            values.put(BaseContract.NotesEntry.BODY,"Cereal");
            values.put(BaseContract.NotesEntry.REFERENCE,"Grocery List");
            mSqliteDatabase.insert("Notes", null, values);

            values.put(BaseContract.NotesEntry._ID,"10");
            values.put(BaseContract.NotesEntry.BODY,"Milk");
            values.put(BaseContract.NotesEntry.REFERENCE,"Grocery List");
            mSqliteDatabase.insert("Notes", null, values);

        }
        */
        //String[] columns = {UID, name, description};
        //Cursor cursor = sqLiteDatabase.query(TABLE1_NAME,columns,null,null,null,null,null);
        //String[] columns = {UID, reference, body};
        //String test = "To Do";
        //Cursor cursor = sqLiteDatabase.query(TABLE2_NAME,columns,reference+" = '"+test+"'",null,null,null,null);
        //StringBuffer buffer = new StringBuffer();
        //while (cursor.moveToNext()) {
        //    int index1 = cursor.getColumnIndex(reference);
        //    int index2 = cursor.getColumnIndex(body);
        //    //int cid = cursor.getInt(0);
        //    String ref = cursor.getString(index1);
        //    String contents = cursor.getString(index2);
        //    //buffer.append(cid+ " "+name +" "+desc +"\n");
        //    buffer.append(ref +" "+contents +"\n");
        //    String data = buffer.toString();
        //    Message.message(context, data);


        //}





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
