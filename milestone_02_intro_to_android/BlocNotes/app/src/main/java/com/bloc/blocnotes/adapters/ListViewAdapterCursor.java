package com.bloc.blocnotes.adapters;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.bloc.blocnotes.util.ImageLoader;
import com.bloc.blocnotes.util.ReminderReceiver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Wayne on 10/27/2014.
 */
public class ListViewAdapterCursor extends SimpleCursorAdapter implements View.OnClickListener{
    LayoutInflater inflater;
    Context context;
    private int layout = R.layout.custom_adapter_notes; //this is the view we use... can create our own view
    private ImageLoader imgLoader;

    public ListViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
        super(context, layout, c, from, to, 0);

        this.inflater = LayoutInflater.from(context);//to inflate a view
        this.context = context;
    }

    @Override
    public Object getItem(int position) {//this method returns a notebook
        getCursor().moveToPosition(position);//this cursor contains our data, moving this to selected position by user

        //then gets data from this position
        Note note = new Note();
        note.setId(getCursor().getLong(getCursor().getColumnIndex(BaseContract.NotesEntry._ID)));//this id is generated automatically,
        note.setBody(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.BODY)));
        note.setReference(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.REFERENCE)));
        note.setImageUrl(getCursor().getString(getCursor().getColumnIndex(BaseContract.NotesEntry.IMAGE_URL)));

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
        popupMenu.getMenu().add(Menu.NONE, 4, Menu.NONE, "Add image for note");

        ImageView imageViewUrl = (ImageView)view.findViewById(R.id.imageViewUrl);
        imageViewUrl.setFocusable(false);
        //passing url to imageview
        String newUrl = cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.IMAGE_URL));
        imageViewUrl.setTag(newUrl);
        //passing imageview with url to Asynctask
        //new LoadUrl().execute(imageViewUrl);

        //This is where we call the utilities for images from cache
        imgLoader = new ImageLoader(context);
        imgLoader.DisplayImage(newUrl,imageViewUrl);



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
                        remindMe(mPosition);
                        break;
                    case 4:
                        // image URL call here
                        addUrl(mPosition);
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



    public void remindMe(int position) {

        Note note = (Note)getItem(position);

        Log.e("note", note.getBody());//here it is correct

        Intent reminderReceiverIntent = new Intent(context, ReminderReceiver.class);
        reminderReceiverIntent.setAction("SHOW_NOTIFICATION");
        //reminderReceiverIntent.putExtra("EXTRA_REMINDER_TITLE", "Note is due for editing");
        reminderReceiverIntent.putExtra(context.getString(R.string.note_parameter_notification), note);
        //reminderReceiverIntent.putExtra("noteText", noteText);
        //String action =  reminderReceiverIntent.getAction();
        // Make a Broadcasting PendingIntent based on the previous
        final PendingIntent reminderPendingIntent = PendingIntent.getBroadcast(context, 0, reminderReceiverIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
/*
        Intent intent = new Intent(context, context.getClass());
        PendingIntent pendingIntent =
                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long currentTimeMillis = System.currentTimeMillis();
        long nextUpdateTimeMillis = currentTimeMillis + 5 * DateUtils.MINUTE_IN_MILLIS;
        Time nextUpdateTime = new Time();
        nextUpdateTime.set(nextUpdateTimeMillis);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, nextUpdateTimeMillis, pendingIntent);
 */

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

    private void addUrl(int mPosition) {
        final Note currentNote = (Note) getItem(mPosition);
        final EditText userInput = new EditText(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add url where image is located")
                .setView(userInput)
                .setMessage("Image URL Address")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        currentNote.setImageUrl(userInput.getText().toString());
                        NotesDao dao = new NotesDao(context);
                        dao.update(currentNote);
                    }
                });
        builder.show();
    }



    @Override
    public void onClick(View view) {

    }

    /*public void saveImageToSD(Bitmap image, String name) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == false) {
            return;
        }

        ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, imageBytes);
        File extCache = Environment.getDownloadCacheDirectory();
                File cachedImageFile = new File(extCache.getAbsolutePath()
                + File.separator + name);
        try {
            cachedImageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(cachedImageFile);
            fos.write(imageBytes.toByteArray());
            fos.close();
            Message.message(context, "File name = " + fos.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //next lines test storage of image to SD storage directory
        File extStore= Environment.getExternalStorageDirectory();
        File storedImageFile = new File (extStore.getAbsolutePath()
                + File.separator + name);
        try {
            storedImageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(storedImageFile);
            fos.write(imageBytes.toByteArray());
            fos.close();
            Message.message(context, "File name = " + fos.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap bitmapFromCache(String name) {
        String extState = Environment.getExternalStorageState();
        if (!(extState.equals(Environment.MEDIA_MOUNTED) ||
                extState.equals(Environment.MEDIA_MOUNTED_READ_ONLY))) {
            return null;
        }
        String photoPath = Environment.getDownloadCacheDirectory() + File.separator + name;
        File photoFile = new File(photoPath);

        // Check if the file exists
        if (photoFile.exists() == false) {
            // Returning null signifies that the file is not in cache
            return null;
        }
        // Re-create the bitmap from the raw data saved during `saveImageToSD`
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(photoPath, options);
    }

    private class LoadUrl extends AsyncTask<ImageView, Void, Drawable>{
        ImageView imageView;
        Drawable imageLoaded;

        protected Drawable doInBackground(ImageView... params) {
            imageView = params[0];

            //Check SD card for image to see if it exists... call bitmapFromCache to check and return image here
            //then create an if statement or try/catch to run code if it doesn't exist
            Bitmap currentImage = bitmapFromCache((String) imageView.getTag());
            if (currentImage != null) {
                imageLoaded = new BitmapDrawable(currentImage);
            }

            if (currentImage == null) {
                try {
                    String currentUrl = (String)imageView.getTag();
                    URL url = new URL(currentUrl);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    //Bitmap currentImage = BitmapFactory.decodeStream(inputStream);  commented out temporarily with if statement running
                    currentImage = BitmapFactory.decodeStream(inputStream);
                    imageLoaded = new BitmapDrawable(currentImage);
                    saveImageToSD(currentImage,currentUrl);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



            //...here is the code to load from url save on sd and pass path to create drawable
            //test image is loaded in sd
            //if yes load and return to onpostexecute

            //if not load from url, save in sd an return to onpostexecute
            return imageLoaded;
        }

        @Override
        protected void onPostExecute(Drawable imageLoaded) {
            super.onPostExecute(imageLoaded);
            //after load image on background method pass image to imageview here.
            if (imageLoaded == null) {
                //assign default icon/image to imageLoaded
            } else {
                imageView.setImageDrawable(imageLoaded);
            }
        }
    }
    */
}
