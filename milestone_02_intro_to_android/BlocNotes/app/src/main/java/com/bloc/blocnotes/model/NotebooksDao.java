package com.bloc.blocnotes.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bloc.blocnotes.bd.BaseContract;

import java.util.ArrayList;

/**
 * Created by Wayne on 10/25/2014.
 */
public class NotebooksDao {
    private Context context;

    public NotebooksDao(Context context) { //initializing context
        this.context = context;
    }

    public void insert(Notebook notebook){ //now we create an insert for a notebook object
        ContentValues values = new ContentValues();


        values.put(BaseContract.NotebooksEntry.NAME, notebook.getName());//remember this ok? we're linking name of tables for the object to represent
        values.put(BaseContract.NotebooksEntry.DESCRIPTION, notebook.getDescription());

        ContentUris.parseId(context.getContentResolver().insert(BaseContract.NotebooksEntry.URI, values)); //uri identifies our table and put values in it
    }

    public ArrayList<Notebook> getAllNotebooks(){
        Cursor cursor = context.getContentResolver().query(BaseContract.NotebooksEntry.URI,
                null,
                null,//all
                null,//all
                BaseContract.NotebooksEntry.NAME);//sort by name

        ArrayList<Notebook> list = new ArrayList<Notebook>();

        if (cursor.moveToFirst()) {
            do {
                Notebook notebook = new Notebook();
                //we no need all data here, because data in memory is a problem
                //but we need the id, because the id is the primary key
                //with the id we can find the rest of data
                //notebook.setId(cursor.getLong(cursor.getColumnIndex(BaseContract.NotebooksEntry._ID)));//this id is generated automatically,
                notebook.setName(cursor.getString(cursor.getColumnIndex(BaseContract.NotebooksEntry.NAME)));
                //notebook.setDescription(cursor.getString(cursor.getColumnIndex(BaseContract.NotebooksEntry.DESCRIPTION)));


                list.add(notebook);
            } while (cursor.moveToNext());

        }
        cursor.close();

        return list;
    }
}
