package com.bloc.blocnotes;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.util.TypedValue;

import com.bloc.blocnotes.model.Notebook;
import com.bloc.blocnotes.model.NotebooksDao;
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
                    .replace(R.id.container, PlaceholderFragment.newInstance(0))
                    .commit();

            //restoreFontType();//and call it oncreate
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Wayne","onResume was called");
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
        CharSequence userText = textBox.getText();
        //Typeface fontName = textBox.getTypeface();
        float fontSize = textBox.getTextSize();
        outState.putCharSequence("savedText", userText);
        //Toast.makeText(this, "the fontName is " + fontName,Toast.LENGTH_SHORT).show();
        outState.putFloat("fontSize", fontSize);




    }

    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Wayne","onRestoreInstanceState was called");

        SharedPreferences sharedPrefs = this.getSharedPreferences("Custom",Context.MODE_PRIVATE);
        String defaultValue = "Serif";
        String fontName = sharedPrefs.getString("fontName", defaultValue);
        //Toast.makeText(this,"fontName from prefs file = " +fontName,Toast.LENGTH_LONG).show();

        final EditText textBox = (EditText) findViewById(R.id.text);
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        float fontSize = savedInstanceState.getFloat("fontSize");
        textBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        textBox.setText(userText);

        if (fontName == "Default") {
            textBox.setTypeface(Typeface.DEFAULT);
        } else {
            if (fontName == "Serif") {
                textBox.setTypeface(Typeface.SERIF);
            } else {
                if (fontName == "Sans Serif") {
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
                    .replace(R.id.container, NotesFragment.newInstance(selectedNotebook))//here we pass a notebook from list, but not call get name yet
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
                //openMap();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                //getNotes();
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
            final EditText textBox = (EditText) findViewById(R.id.text);
            textBox.setText("  ");
            Log.d("Wayne", "textBox =" + textBox.getText().toString());
        }
        if (id == R.id.action_add) {
            newNotebook();
        }
        if (id == R.id.action_style) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
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
                //Message.message(getBaseContext(), "newText = " + newText);
                mNewNotebook=newText.toString();
                //Message.message(getBaseContext(), "mNewNotebook = " + mNewNotebook);
                insertNewNotebook();
            }
        });
        builder.show();

    }


    public void insertNewNotebook() {
        Notebook notebook = new Notebook(this);
        notebook.setName(mNewNotebook);
        notebook.setLoaded(true);

        NotebooksDao notebooksDao = new NotebooksDao(this);
        notebooksDao.insert(notebook);

        //Message.message(this, "inserted");
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
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bloc_notes, container, false);

            EditText editText = (EditText)rootView.findViewById(R.id.text);
            Utilities.restoreFontType(getActivity(), editText);
            Utilities.restoreFontSize(getActivity(), editText);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((BlocNotes) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
