package com.bloc.blocspot.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.bloc.blocspot.blocspot.R;

import com.bloc.blocspot.database.BaseContract;
import com.bloc.blocspot.model.Place;

import com.bloc.blocspot.adapters.ListViewAdapterCursor;

/**
 * Created by Wayne on 11/23/2014.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static String keyPlace = "keyPlace";
    private ListView listView;
    private ListViewAdapterCursor mAdapter;
    //declare empty view layout and the image view
    //private ImageView imageViewAddNote;
    //private ImageView imageViewAddNote1;
    //private RelativeLayout mylayout;
    private static String mCategory;

    public static ListFragment newInstance(Place selectedPlace) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        //the fist argument is to identify the arg to find it later
        //this is correct way
        //args.putSerializable(keyPlace, selectedPlace);
        fragment.setArguments(args);

        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R
                .layout.fragment_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.placesListView);
        //String[] places = getResources().getStringArray(R.array.places);
        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, places);
        //listView.setAdapter(adapter);

        mAdapter = new ListViewAdapterCursor(getActivity(), 0, null, new String[]{}, new int[]{});

        getLoaderManager().initLoader(0, null, this);


        listView = (ListView) rootView.findViewById(R.id.placesListView);
        listView.setAdapter(mAdapter);



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.findItem(R.id.action_map).setVisible(true);
        //menu.findItem(R.id.action_list).setVisible(false);
        //menu.findItem(R.id.action_favorites_map).setVisible(false);

    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        /*
        String selection = BaseContract.PlacesEntry.NAME + " = ?";

        Place selectedPlace = (Place) getArguments().getSerializable(keyPlace);

        String[] selectionArgs = new String[]{selectedPlace.getName()};//only here we call get name, here the data been loaded
        */
        return new CursorLoader(
                getActivity(),   // Parent activity context
                BaseContract.PlacesEntry.URI,        // Table to query
                null,     // Projection to return
                null,            // NotesEntry.Reference data...actual notebook reference here
                null,            // selectedNotebook name
                null// Default sort order//sorting by name
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
        //now when load is finished, we can check if there is data in cursor

        // maybe use this for category if there are no places in category...to add a place...
        //like adding a note in BlocNotes
         /*
        if (cursor.getCount() > 0) {
            mylayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            imageViewAddNote1.setVisibility(View.VISIBLE);
        } else {
            mylayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            imageViewAddNote1.setVisibility(View.GONE);
        }
        */

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }


    /*
    private void newDescription() {
        Place selectedPlace = (Place) getArguments().getSerializable(keyPlace);
        mCategory = selectedPlace.getName();
        ((BlocSpotActivity) getActivity()).createNewDescription(mPlace);
        Message.message(getActivity(), "Description was added to " + mPlace + " place");
    }
    */
}
