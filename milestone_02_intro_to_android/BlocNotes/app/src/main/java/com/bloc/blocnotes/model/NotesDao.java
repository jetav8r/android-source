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

    public void update(Note note){
        ContentValues values = new ContentValues();
        values.put(BaseContract.NotesEntry.BODY, note.getBody());
        values.put(BaseContract.NotesEntry.REFERENCE, note.getReference());

        //to update a note we use a where clause and _ID to find it in the database
        String selection = BaseContract.NotesEntry._ID + " = ? ";
        String[] selectionArgs = new String[]{ String.valueOf(note.getId())};

        context.getContentResolver().update(BaseContract.NotesEntry.URI, values, selection, selectionArgs);
    }

    public void delete(Note note){
        context.getContentResolver().delete(BaseContract.NotesEntry.URI, BaseContract.NotesEntry._ID + " = " + note.getId(), null);
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

    public Note getNote(long id){
        String selection = BaseContract.NotesEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Note note = new Note();

        Cursor cursor = context.getContentResolver().query(BaseContract.NotesEntry.URI,
                null,
                selection,//all
                selectionArgs,//all
                null);//this query returns only one

        if(cursor.moveToFirst()){

            //when we load data
            //we need to marks loaded
            note.setId(cursor.getLong(cursor.getColumnIndex(BaseContract.NotesEntry._ID)));//this id is generated automatically,
            note.setBody(cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.BODY)));
            note.setReference(cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.REFERENCE)));
        }
        return note;
    }
}
