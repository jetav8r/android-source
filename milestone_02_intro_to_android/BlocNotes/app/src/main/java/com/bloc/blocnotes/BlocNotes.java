package com.bloc.blocnotes;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BlocNotes extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Wayne","onCreate was called");
        setContentView(R.layout.activity_bloc_notes);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
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
        outState.putCharSequence("savedText", userText);
    }

    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Wayne","onRestoreInstanceState was called");
        final EditText textBox = (EditText) findViewById(R.id.text);
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        textBox.setText(userText);


    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                comingSoon();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                openMap();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

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
            return true;
        }
        if (id == R.id.action_erase) {
            final EditText textBox = (EditText) findViewById(R.id.text);
            textBox.setText("  ");
            Log.d("Wayne", "textBox =" + textBox.getText().toString());
        }
        if (id == R.id.action_add) {
            comingSoon();
        }
        if (id == R.id.action_style) {
            showDialog();
            //onStyleChange();
        }
        return super.onOptionsItemSelected(item);
    }

    private void comingSoon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Notebook");
        builder.setMessage("Coming Soon!!");
        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
        //new AlertDialog.Builder(this).setTitle("Add Notebook").setMessage("Coming Soon").setNeutralButton("OK",
               //new DialogInterface.OnClickListener() {
                    //@Override
                   // public void onClick(DialogInterface dialogInterface, int i) {
                   // }
                //}).show();
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

    public void showDialog() {
        FragmentManager manager = getFragmentManager();
        CustomStyleDialogFragment myStyle = new CustomStyleDialogFragment();
        myStyle.show(manager, "CustomStyle");
        Toast.makeText(this,"myStyle was launched", Toast.LENGTH_SHORT).show();
        }

    public void onStyleChange(CustomStyleDialogFragment dialog, int styleId) {
        Toast.makeText(this,"TESTING STYLE CHANGE",Toast.LENGTH_LONG).show();
    }

    public void onFontChange(CustomStyleDialogFragment dialog, String fontName) {
        Toast.makeText(this,"TESTING FONT CHANGE",Toast.LENGTH_LONG).show();
    }

    public void onThemeChange(CustomStyleDialogFragment dialog, int themeId) {
        Toast.makeText(this,"TESTING THEME CHANGE",Toast.LENGTH_LONG).show();
    }

    public void onDialogMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onStyleChange(View view) {
        Toast.makeText(this,"Using XML on click call",Toast.LENGTH_LONG).show();
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
