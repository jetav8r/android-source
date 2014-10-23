package com.bloc.blocnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by Wayne on 9/27/2014.
 */


public class BlocNotesHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "blocnotes";
    public static final String TABLE1_NAME = "notebooks";
    public static final String TABLE2_NAME = "notes";
    public static final int DATABASE_VERSION = 1;
    public static final String UID = "_id";
    public static final String name = "name";
    public static final String body = "body";
    public static final String description = "description";
    public static final String reference = "notebook_reference";
    public static final String DROP_TABLE_1 = "DROP TABLE IF EXISTS " + TABLE1_NAME + "";
    public static final String DROP_TABLE_2 = "DROP TABLE IF EXISTS " + TABLE2_NAME + "";

    public static final String CREATE_NOTEBOOKS =
            "CREATE TABLE " + TABLE1_NAME +
                    "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + name + " VARCHAR(255)," +
                    description + " VARCHAR(255)" + " )";
    public static final String CREATE_NOTES =
            "CREATE TABLE " + TABLE2_NAME +
                    "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT," + body + " VARCHAR(255)," +
                    reference + " VARCHAR(255)" + " )";

    public BlocNotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Message.message(context, "constructor was called");
    }

    public Cursor getAllNotebooks(){
        String[] columns = {UID, name, description};
        return this.getWritableDatabase().query(TABLE1_NAME,columns,null,null,null,null,null);
    }

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

        values.put(UID, "2");
        values.put(name, "Dreams");
        values.put(description, "Dreams Notebook");
        sqLiteDatabase.insert("Notebooks", null, values);
        values.clear();

        values.put(UID, "3");
        values.put(name, "To Do");
        values.put(description, "To Do List");
        sqLiteDatabase.insert("Notebooks", null, values);
        values.clear();

        values.put(UID, "4");
        values.put(name, "Goals");
        values.put(description, "Long Term Goals");
        sqLiteDatabase.insert("Notebooks", null, values);
        values.clear();

        values.put(UID, "5");
        values.put(name, "Grocery List");
        values.put(description, "Grocery List");
        sqLiteDatabase.insert("Notebooks", null, values);
        values.clear();

        values.put(UID, "1");
        values.put(body, "Begin typing note here");
        values.put(reference, "Uncategorized");
        sqLiteDatabase.insert("Notes", null, values);

        values.put(UID, "2");
        values.put(body, "I had a dream that I drank the worlds' biggest margarita.  When woke up I was hugging the toilet");
        values.put(reference, "Dreams");
        sqLiteDatabase.insert("Notes", null, values);

        values.put(UID, "3");
        values.put(body, "My dream last night was that I had 45 dogs in my house!");
        values.put(reference, "Dreams");
        sqLiteDatabase.insert("Notes", null, values);

        values.put(UID, "4");
        values.put(body, "I had a dream that I was dreaming");
        values.put(reference, "Dreams");
        sqLiteDatabase.insert("Notes", null, values);

        values.put(UID, "5");
        values.put(body, "Find grant writer for dog stuff");
        values.put(reference, "To Do");
        sqLiteDatabase.insert("Notes", null, values);

        values.put(UID, "6");
        values.put(body, "Finish fixing shifter-karts");
        values.put(reference, "To Do");
        sqLiteDatabase.insert("Notes", null, values);

        String[] columns = {UID, name, description};
        Cursor cursor = sqLiteDatabase.query(TABLE1_NAME,columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);
            buffer.append(cid+ " "+name +" "+desc +"\n");
            String data = buffer.toString();
            Message.message(context, data);


        }
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
