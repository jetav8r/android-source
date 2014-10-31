package com.bloc.blocnotes.background;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.bloc.blocnotes.bd.BaseContract;
import com.bloc.blocnotes.model.Notebook;

/**
 * Created by Wayne on 10/30/2014.
 */
public class InsertNotebook {
    Context context;
    public void insert(final Notebook notebook){ //now we create an insert for a notebook object

        new AsyncTask<Notebook, Void, Void>(){
            @Override
            protected Void doInBackground(Notebook... notebooks) {

                Notebook mNotebook = notebooks[0];

                ContentValues values = new ContentValues();


                values.put(BaseContract.NotebooksEntry.NAME, mNotebook.getName());//remember this ok? we're linking name of tables for the object to represent
                values.put(BaseContract.NotebooksEntry.DESCRIPTION, mNotebook.getDescription());

                ContentUris.parseId(context.getContentResolver().insert(BaseContract.NotebooksEntry.URI, values)); //uri identifies our table and put values in it
                return null;
            }
        }.execute(notebook);
    }
}
