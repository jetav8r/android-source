package com.bloc.blocnotes.adapters;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bloc.blocnotes.BlocNotes;
import com.bloc.blocnotes.Message;
import com.bloc.blocnotes.R;
import com.bloc.blocnotes.bd.BaseContract;
import com.bloc.blocnotes.model.Note;
import com.bloc.blocnotes.model.NotebooksDao;
import com.bloc.blocnotes.model.NotesDao;
import com.bloc.blocnotes.util.ReminderReceiver;

/**
 * Created by Wayne on 10/27/2014.
 */
public class ListViewAdapterCursor extends SimpleCursorAdapter implements View.OnClickListener{
    LayoutInflater inflater;
    Context context;
    private int layout = R.layout.custom_adapter_notes; //this is the view we use, but we can create our own view

    public ListViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
        super(context, layout, c, from, to, 0);

        this.inflater = LayoutInflater.from(context);//to inflate a view
        this.context = context;
    }

    @Override
    public Object getItem(int position) {//this method returns a notebook
        getCursor().moveToPosition(position);//this cursor contains our data, moving this to selected position by user

        //then gets data from this position
        Note note = new Note(context);
        note.setId(getCursor().getLong(getCursor().getColumnIndex(BaseContract.NotesEntry._ID)));//this id is generated automatically,
        note.setBody(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.BODY)));
        note.setReference(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.REFERENCE)));
        note.setLoaded(true);//to not loop

        return note;// returns it
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
        //at this point cursor is in the position
        //then we can get the position here

        final TextView textView = (TextView)view.findViewById(R.id.textView_body);
        final String text = cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.BODY));
        textView.setText(text);
        final int mPosition = cursor.getPosition();

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView_menu);
        imageView.setFocusable(false);
        final PopupMenu popupMenu = new PopupMenu(context, imageView);
        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Delete note");
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Update note");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "Move note");
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, "Remind me");


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        // so we put delete note here
                        //passing the position
                        deleteNote(mPosition);
                        break;
                    case 1:
                        // we put rename note here
                        updateNote(mPosition);
                        break;
                    case 2:
                        // we put move note here
                        moveNote(mPosition);
                        break;
                    case 3:
                        // remind me call here
                        remindMe();
                        break;
                }
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
    }

    public void remindMe() {
        Intent reminderReceiverIntent = new Intent(context, ReminderReceiver.class);
        reminderReceiverIntent.setAction("SHOW_NOTIFICATION");
        reminderReceiverIntent.putExtra("EXTRA_REMINDER_TITLE", "Note is due for editing");
        //String action =  reminderReceiverIntent.getAction();
        // Make a Broadcasting PendingIntent based on the previous
        final PendingIntent reminderPendingIntent = PendingIntent.getBroadcast(context, 0, reminderReceiverIntent,0);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        final CharSequence[] items = {"5 minutes","10 minutes","30 minutes","60 minutes"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Set Reminder")
        .setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        //5 minute reminder
                        alarmManager.set(AlarmManager.RTC,
                        System.currentTimeMillis() + 3 * 1000, reminderPendingIntent);
                        Toast.makeText(context, "You will be reminded in 5 minutes", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //10 minute reminder
                        alarmManager.set(AlarmManager.RTC,
                        System.currentTimeMillis() + 600 * 1000, reminderPendingIntent);
                        Toast.makeText(context, "You will be reminded in 10 minutes", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //30 minute reminder
                        alarmManager.set(AlarmManager.RTC,
                        System.currentTimeMillis() + 1800 * 1000, reminderPendingIntent);
                        Toast.makeText(context, "You will be reminded in 30 minutes", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //60 minute reminder AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC,
                        System.currentTimeMillis() + 3600 * 1000, reminderPendingIntent);
                        Toast.makeText(context, "You will be reminded in 60 minutes", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void moveNote(final int mPosition) {
        final NotebooksDao notebooksDao = new NotebooksDao(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Notebook")
        .setItems(notebooksDao.getArrayStringNotebooks(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String [] currentReference = notebooksDao.getArrayStringNotebooks();// GET ITEM REFERENCED
                String currentNotebook = currentReference[i];
                Note note = (Note)getItem(mPosition);//this note
                //we need only change reference of note which we get from currentReference array
                note.setReference(currentNotebook);
                NotesDao notesDao = new NotesDao(context);
                notesDao.update(note);
            }
        });
        builder.create();
        builder.show();
    }

    private void updateNote(int mPosition){
        Note currentNote = (Note)getItem(mPosition);
        ((BlocNotes)context).updateNote(currentNote);
    }

    private void deleteNote(int mPosition){
        //put code here
        //the adapter has a method getitem(position) returns a Note
        //then get it, cast and delete
        Note currentNote = (Note)getItem(mPosition);
        NotesDao notesDao = new NotesDao(context);
        notesDao.delete(currentNote);
    }

    @Override
    public long getItemId(int position) {//returns id in the position selected
        //mPosition = position;
        //Message.message(context, "position = " + position);
        return super.getItemId(position);
    }

    @Override
    public void onClick(View view) {

    }
}
