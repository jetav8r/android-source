package com.bloc.blocnotes.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.bloc.blocnotes.Message;
import com.bloc.blocnotes.R;
import com.bloc.blocnotes.bd.BaseContract;
import com.bloc.blocnotes.model.Note;
import com.bloc.blocnotes.model.Notebook;
import com.bloc.blocnotes.model.NotebooksDao;
import com.bloc.blocnotes.model.NotesDao;

/**
 * Created by Wayne on 10/30/2014.
 */
public class Utilities {

    public static void restoreFontType(Context context, EditText view) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("Custom", Context.MODE_PRIVATE);

        String fontName = sharedPrefs.getString(context.getString(R.string.pref_font_name), "Default");//if not saved set default
        //String fontName = "Sans Serif";
        //now change app style font here
        //EditText text = (EditText) this.findViewById(R.id.text);z
        if (fontName == "Default") {
            view.setTypeface(Typeface.DEFAULT);
        } else {
            if (fontName == "Serif") {
                view.setTypeface(Typeface.SERIF);
            } else {
                if (fontName == "Sans Serif") {
                    view.setTypeface(Typeface.SANS_SERIF);
                } else {
                    Typeface currentFont = Typeface.createFromAsset(context.getAssets(), fontName);
                    view.setTypeface(currentFont);


                    //Message.message(context, "currentFont variable = " + currentFont);
                }
            }
        }
    }

    public static void restoreFontSize(Context context, EditText view) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("Custom", Context.MODE_PRIVATE);
        int fontSize = sharedPrefs.getInt(context.getString(R.string.pref_font_size), 44);//if not saved set default
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
    }

    public static void createInitialDatabase(final Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences("Custom", Context.MODE_PRIVATE);

        boolean isCreated = sharedPrefs.getBoolean(context.getString(R.string.pref_database_created), false);

        if(isCreated){
            return;
        }


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                NotebooksDao notebooksDao = new NotebooksDao(context);
                Notebook notebook = new Notebook();

                //in the insert we not need pass id because it is created automatically
                notebook.setName("Uncategorized");
                notebook.setDescription("Default Notebook");
                notebooksDao.insert(notebook);

                notebook.setName("Dreams");
                notebook.setDescription("Dreams Notebook");
                notebooksDao.insert(notebook);

                notebook.setName("To Do");
                notebook.setDescription("To Do List");
                notebooksDao.insert(notebook);

                notebook.setName("Goals");
                notebook.setDescription("Long Term Goals");
                notebooksDao.insert(notebook);

                notebook.setName("Grocery List");
                notebook.setDescription("Grocery List");
                notebooksDao.insert(notebook);

                NotesDao notesDao = new NotesDao(context);
                Note note = new Note();

                note.setBody("Default location for notes here");//place for notes with no notebook name assigned
                note.setReference("Uncategorized");
                //inserting
                notesDao.insert(note);

                note.setBody("I had a dream that I drank the worlds' biggest margarita.  When woke up I was hugging the toilet");
                note.setReference("Dreams");
                notesDao.insert(note);

                note.setBody("My dream last night was that I had 45 dogs in my house!");
                note.setReference("Dreams");
                notesDao.insert(note);

                note.setBody("I had a dream that I was dreaming");
                note.setReference("Dreams");
                notesDao.insert(note);

                note.setBody("Find grant writer for dog stuff");
                note.setReference("To Do");
                notesDao.insert(note);

                note.setBody("Finish fixing shifter-karts");
                note.setReference("To Do");
                notesDao.insert(note);

                note.setBody("Build Sanctuary for rescued dogs");
                note.setReference("Goals");
                notesDao.insert(note);

                note.setBody("Become good at Android programming");
                note.setReference("Goals");
                notesDao.insert(note);

                note.setBody("Cereal");
                note.setReference("Grocery List");
                notesDao.insert(note);

                note.setBody("Milk");
                note.setReference("Grocery List");
                notesDao.insert(note);

                return null;
            }
        }.execute();

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(context.getString(R.string.pref_database_created), true);
        editor.commit();

    }
}
