package com.bloc.blocnotes.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

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
                values.put(BaseContract.NotebooksEntry.NAME, notebook.getName());//we're linking name of tables for the object to represent
                values.put(BaseContract.NotebooksEntry.DESCRIPTION, notebook.getDescription());
                context.getContentResolver().insert(BaseContract.NotebooksEntry.URI, values); //uri identifies our table and put values in it
    }

    public void update(final Notebook notebook){

                ContentValues values = new ContentValues();
                values.put(BaseContract.NotebooksEntry.NAME, notebook.getName());
                values.put(BaseContract.NotebooksEntry.DESCRIPTION, notebook.getDescription());
                //to update a notebook we need a where clause to find it in the database
                //we use it's id
                String selection = BaseContract.NotebooksEntry._ID + " = ? ";
                String[] selectionArgs = new String[]{ String.valueOf(notebook.getId())};
                context.getContentResolver().update(BaseContract.NotebooksEntry.URI, values, selection, selectionArgs);
    }

    public void delete(final Notebook notebook){
                context.getContentResolver().delete(BaseContract.NotebooksEntry.URI, BaseContract.NotebooksEntry._ID + " = " + notebook.getId(), null);
    }

    public String[] getArrayStringNotebooks(){
        ArrayList<Notebook> listNotebooks = getAllNotebooks();
        String[] arrayStringNotebooks = new String[listNotebooks.size()];

        for (int i = 0; i < listNotebooks.size(); i++){
            arrayStringNotebooks[i] = listNotebooks.get(i).getName();
        }
        return arrayStringNotebooks;
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
                notebook.setId(cursor.getLong(cursor.getColumnIndex(BaseContract.NotebooksEntry._ID)));//this id is generated automatically,
                notebook.setName(cursor.getString(cursor.getColumnIndex(BaseContract.NotebooksEntry.NAME)));
                notebook.setDescription(cursor.getString(cursor.getColumnIndex(BaseContract.NotebooksEntry.DESCRIPTION)));
                list.add(notebook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Notebook getNotebook(long id){
        String selection = BaseContract.NotebooksEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Notebook notebook = new Notebook();

        Cursor cursor = context.getContentResolver().query(BaseContract.NotebooksEntry.URI,
                null,
                selection,//all
                selectionArgs,//all
                null);//this query returns only one

        if(cursor.moveToFirst()){

            //when we load data
            //we need to marks loaded
            notebook.setId(cursor.getLong(cursor.getColumnIndex(BaseContract.NotebooksEntry._ID)));//this id is generated automatically,
            notebook.setName(cursor.getString(cursor.getColumnIndex(BaseContract.NotebooksEntry.NAME)));
            notebook.setDescription(cursor.getString(cursor.getColumnIndex(BaseContract.NotebooksEntry.DESCRIPTION)));
        }
        return notebook;
    }
}
