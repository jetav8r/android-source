package com.bloc.blocnotes;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.util.TypedValue;

import com.bloc.blocnotes.bd.BaseProvider;
import com.bloc.blocnotes.fragments.CreateNoteFragment;
import com.bloc.blocnotes.fragments.EditNoteFragment;
import com.bloc.blocnotes.model.Note;
import com.bloc.blocnotes.model.Notebook;
import com.bloc.blocnotes.model.NotebooksDao;
import com.bloc.blocnotes.model.NotesDao;
import com.bloc.blocnotes.util.ReminderReceiver;
import com.bloc.blocnotes.util.Utilities;

import java.util.ArrayList;


public class BlocNotes extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static String mNewNotebook;
    private static Context context;
    //BlocNotesHelper blocNotesHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("Wayne", "onCreate was called");
            setContentView(R.layout.activity_bloc_notes);
            Utilities.createInitialDatabase(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, CreateNoteFragment.newInstance(""))
                    .commit();

            if(getIntent().getExtras() != null){
                Note note = (Note)getIntent().getExtras().getSerializable("note");
                Log.e("Note in BlocNotes on Create", note.getBody());
                updateNote(note);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Wayne","onResume was called");

        if(getIntent().getExtras() != null){
            Note note = (Note)getIntent().getExtras().getSerializable("note");
            Log.e("Note in BlocNotes on Resume", note.getBody());
            updateNote(note);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Wayne","onStart was called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Wayne","onPause was called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Wayne","onStop was called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Wayne","onDestroy was called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Wayne", "onSaveInstanceState was called");
        final EditText textBox = (EditText) findViewById(R.id.text);
        if(textBox != null){
            CharSequence userText = textBox.getText();
            //Typeface fontName = textBox.getTypeface();
            float fontSize = textBox.getTextSize();
            outState.putCharSequence("savedText", userText);
            //Toast.makeText(this, "the fontName is " + fontName,Toast.LENGTH_SHORT).show();
            outState.putFloat("fontSize", fontSize);
        }
    }

    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Wayne","onRestoreInstanceState was called");
        SharedPreferences sharedPrefs = this.getSharedPreferences("Custom",Context.MODE_PRIVATE);
        String defaultValue = "Serif";
        String fontName = sharedPrefs.getString("fontName", defaultValue);
        final EditText textBox = (EditText) findViewById(R.id.text);
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        float fontSize = savedInstanceState.getFloat("fontSize");
        textBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        textBox.setText(userText);
        textBox.requestFocus();

        if (fontName.equals("Default")) {
            textBox.setTypeface(Typeface.DEFAULT);
        } else {
            if (fontName.equals("Serif")) {
                textBox.setTypeface(Typeface.SERIF);
            } else {
                if (fontName.equals("Sans Serif")) {
                    textBox.setTypeface(Typeface.SANS_SERIF);
                } else {
                    Typeface currentFont = Typeface.createFromAsset(this.getAssets(), fontName);
                    textBox.setTypeface(currentFont);
                    //Toast.makeText(this, "the fontName is " + fontName,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        NotebooksDao notebooksDao = new NotebooksDao(this);
        ArrayList<Notebook> listNotebooks = new ArrayList<Notebook>();
        listNotebooks = notebooksDao.getAllNotebooks();

        //at this point we don't have data in database
        ///then we cannot access zero position of array list, is null
        //we need to create a test first
        if(!listNotebooks.isEmpty()){//if not empty
            Notebook selectedNotebook =listNotebooks.get(position);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.note_in, R.anim.note_out, R.anim.note_in, R.anim.note_out)
                            //here we pass a notebook from list, but don't call getname yet
                    .replace(R.id.container, NotesFragment.newInstance(selectedNotebook))
                    .addToBackStack("Notes")
                    .commit();
        }//else do nothing


        //Message.message(this, selectedNotebook.getName());
        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        */
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

        //StringBuffer buffer = new StringBuffer();
        //while (cursor.moveToNext()) {
            //int index1 = cursor.getColumnIndex(BlocNotesHelper.reference);
            //int index2 = cursor.getColumnIndex(BlocNotesHelper.body);
            //int cid = cursor.getInt(0);
            //String ref = cursor.getString(index1);
            //String contents = cursor.getString(index2);
            //buffer.append(cid+ " "+name +" "+desc +"\n");
            //buffer.append(ref + " " + contents + "\n");
            //String data = buffer.toString();
            //Message.message(this, data);
        //}
        //return cursor;
    //}

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.bloc_notes, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            showPrefs();
            return true;
        }
        if (id == R.id.action_erase) {
            textErase();
        }
        if (id == R.id.action_add) {
            newNotebook();
        }
        if (id == R.id.action_style) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void textErase() {
        final EditText textBox = (EditText) findViewById(R.id.text);
        textBox.setText("");
        textBox.requestFocus();
        Log.d("Wayne", "textBox =" + textBox.getText().toString());
    }

    private void newNotebook() {
        final EditText userInput = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Notebook");
        builder.setView(userInput);
        builder.setMessage("Notebook Name?");
        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Editable newText = userInput.getText();
                mNewNotebook=newText.toString();
                insertNewNotebook();
            }
        });
        builder.show();
    }


    public void insertNewNotebook() {
        Notebook notebook = new Notebook();
        notebook.setName(mNewNotebook);
        NotebooksDao notebooksDao = new NotebooksDao(this);
        notebooksDao.insert(notebook);

    }

    private void openMap() {
        double latitude = 32.715000;
        double longitude = -117.162500;
        String label = "Starting point";
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void showPrefs() {
        FragmentManager manager = getFragmentManager();
        PrefFragment myPrefs = new PrefFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, myPrefs);
        fragmentTransaction.addToBackStack("Preferences");
        fragmentTransaction.commit();
    }
    public void showDialog() {
        FragmentManager manager = getFragmentManager();
        CustomStyleDialogFragment myStyle = new CustomStyleDialogFragment();
        myStyle.show(manager, "CustomStyle");
        }

    public void createNewNote(String notebookName){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, CreateNoteFragment.newInstance(notebookName))
                .commit();
    }

    public void updateNote(Note note){
        ///replacing the container with the edit fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, EditNoteFragment.newInstance(note))
                .commit();
    }
}
