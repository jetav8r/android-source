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
public class NotesDao {//Dao is Data Access Object
    private Context context;

    public NotesDao(Context context){ //initializing context
        this.context = context;
    }

    public void insert(Note note){ //now we create a insert for a note object
        ContentValues values = new ContentValues();


        values.put(BaseContract.NotesEntry.BODY, note.getBody());//remember this ok? we're linking name of tables to object represent
        values.put(BaseContract.NotesEntry.REFERENCE, note.getReference());

        ContentUris.parseId(context.getContentResolver().insert(BaseContract.NotesEntry.URI, values)); //uri identifies our table and put values in it
    }

    public ArrayList<Note> getAllNotes(){
        Cursor cursor = context.getContentResolver().query(BaseContract.NotesEntry.URI,
                null,
                null,//all
                null,//all
                BaseContract.NotesEntry.BODY);//sort by name

        ArrayList<Note> list = new ArrayList<Note>();

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(BaseContract.NotesEntry._ID)));//this id is generated automatically,
                note.setBody(cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.BODY)));
                note.setReference(cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.REFERENCE)));
                list.add(note);
            } while (cursor.moveToNext());

        }
        cursor.close();

        return list;
    }
}
