package com.bloc.blocnotes.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bloc.blocnotes.bd.BaseContract;
import com.bloc.blocnotes.model.Note;

/**
 * Created by Wayne on 10/27/2014.
 */
public class ListViewAdapterCursor extends SimpleCursorAdapter {
    LayoutInflater inflater;
    Context context;
    private int layout = android.R.layout.simple_list_item_activated_1; //this is the view we use, but we can create our own view

    public ListViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
        super(context, layout, c, from, to, 0);

        this.inflater = LayoutInflater.from(context);//to inflate a view
        this.context = context;
    }

    @Override
    public Object getItem(int position) {//this method returns an notebook
        getCursor().moveToPosition(position);//this cursor contains our data, moving this to selected position by user

        //then get data from this position
        Note note = new Note();
        note.setId(getCursor().getLong(getCursor().getColumnIndex(BaseContract.NotesEntry._ID)));//this id is generated automaticaly,
        note.setBody(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.BODY)));
        note.setReference(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.REFERENCE)));

        return note;//finally returns it
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {//this creates a view to show to user
        View view = inflater.inflate(layout, null);
        return view;//simple create and return view
    }

    //now we bind data to view


    @Override
    public void bindView(View view, Context context, Cursor cursor) {//this view is returned for method newview
        super.bindView(view, context, cursor);

        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setText(cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.BODY)));
    }

    @Override
    public long getItemId(int position) {//simpre retuns id in the position selected
        return super.getItemId(position);
    }
}
