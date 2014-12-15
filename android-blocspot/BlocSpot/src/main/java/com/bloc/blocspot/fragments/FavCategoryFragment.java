package com.bloc.blocspot.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.adapters.FavoriteCategoryViewAdapterCursor;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.database.BaseContract;
import com.bloc.blocspot.model.Category;

public class FavCategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private ListView mListView;
    private FavoriteCategoryViewAdapterCursor mAdapter;
    private TextView mTextView;
    private String mFavCategoryName;

    public static FavCategoryFragment newInstance() {
        FavCategoryFragment fragment = new FavCategoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public FavCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R
                .layout.fragment_fav_category, container, false);

        mAdapter = new FavoriteCategoryViewAdapterCursor(getActivity(), 0, null, new String[]{}, new int[]{});

        mListView =  (ListView) rootView.findViewById(R.id.fav_cat_listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mTextView = (TextView) rootView.findViewById(R.id.textViewFavCategoryName);

        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
         public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.findItem(R.id.action_map).setVisible(true);
        //menu.findItem(R.id.action_list).setVisible(false);
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
                BaseContract.CategoriesEntry.URI,        // Table to query
                null,     // Projection to return
                BaseContract.CategoriesEntry.FAVORITE + " = 'Y'",
                null,            // selected name
                BaseContract.CategoriesEntry.FRIENDLY_NAME//sort by name//
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



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Category category = (Category)mAdapter.getItem(position);
        mFavCategoryName = category.getGoogle_name();
        Log.e("Test", "fav cat  name = " + mFavCategoryName);
        ((BlocSpotActivity)getActivity()).listFavPlaces(mFavCategoryName);
        //((BlocSpotActivity)getActivity()).removeFragments();
    }
}
