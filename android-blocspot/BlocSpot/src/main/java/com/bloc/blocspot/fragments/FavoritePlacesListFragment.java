package com.bloc.blocspot.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bloc.blocspot.adapters.FavoritePlacesViewAdapterCursor;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.database.BaseContract;

/**
 * Created by Wayne on 12/5/2014.
 */
public class FavoritePlacesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, TextWatcher {
    private ListView mListView;
    private FavoritePlacesViewAdapterCursor mAdapter;
    private static String mCategory;
    private TextView mTextView;
    private TextView mTextView2;
    private TextView mTextView3;
    private ImageView mImageView;
    private Context context;
    public String mNewCategory;
    //private EditText editTextFilter2;
    private String mCategoryName;
    private static String CATEGORY_NAME = "category_name";


    public static FavoritePlacesListFragment newInstance(String categoryName) {
        FavoritePlacesListFragment fragment = new FavoritePlacesListFragment();
        Bundle args = new Bundle();
        //the fist argument is to identify the arg to find it later
        //this is correct way
        args.putString(CATEGORY_NAME, categoryName);
        fragment.setArguments(args);

        return fragment;
    }

    public FavoritePlacesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R
                .layout.fragment_favorite_places_list, container, false);

        //String[] places = getResources().getStringArray(R.array.places);
        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, places);
        //listView.setAdapter(adapter);

        mAdapter = new FavoritePlacesViewAdapterCursor(getActivity(), 0, null, new String[]{}, new int[]{});

        mListView = (ListView) rootView.findViewById(R.id.favorite_placesListView);
        mListView.setAdapter(mAdapter);
        //mListView.setOnItemClickListener(this);

        //mImageView = (ImageView) rootView.findViewById(R.id.imageView_add_cat);
        //mImageView.setOnClickListener(this);

        mTextView = (TextView) rootView.findViewById(R.id.textView_fav_place_name);
        //mTextView2 = (TextView) rootView.findViewById(R.id.textView_fav_category);
        mTextView3 = (TextView) rootView.findViewById(R.id.textView_fav_notes);




        //editTextFilter2 = (EditText)rootView.findViewById(R.id.editText_filter2);
        //editTextFilter2.setFocusable(true);
        //editTextFilter2.requestFocus();
        //editTextFilter2.addTextChangedListener(this);


        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.findItem(R.id.action_favorites_map).setVisible(true);
        //menu.findItem(R.id.action_list).setVisible(false);
        //menu.findItem(R.id.action_map).setVisible(true);

    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


            String selection = BaseContract.PlacesEntry.FAV_CATEGORY +  " = ?";
            String[] selectionArgs = new String[]{getArguments().getString(CATEGORY_NAME)};

            return new CursorLoader(
                    getActivity(),   // Parent activity context
                    BaseContract.PlacesEntry.URI,        // Table to query
                    null,     // Projection to return
                    selection,            // CategoriesEntry.Reference data...actual notebook reference here
                    selectionArgs,        // selectedCategory name
                    BaseContract.PlacesEntry.NAME// Default sort order//sorting by name
            );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
        //now when load is finished, we can check if there is data in cursor

        // maybe use this for category if there are no places in category...to add a place...
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //Log.e("Text changed", editTextFilter.getText().toString());
        getLoaderManager().restartLoader(0, null, this);
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Category category = (Category)mAdapter.getItem(position);
        mCategoryName = category.getFriendly_name();
        Log.e("Test", "googleName = "+ mCategoryName);
        ((BlocSpotActivity)getActivity()).currentLocation(mCategoryName);
        ((BlocSpotActivity)getActivity()).removeFragments();
    }
    */
    /*
    private void newDescription() {
        Place selectedPlace = (Place) getArguments().getSerializable(keyPlace);
        mCategory = selectedPlace.getName();
        ((BlocSpotActivity) getActivity()).createNewDescription(mPlace);
        Message.message(getActivity(), "Description was added to " + mPlace + " place");
    }
    */
}