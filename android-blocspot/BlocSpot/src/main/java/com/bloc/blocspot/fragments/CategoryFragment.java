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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.adapters.CategoryViewAdapterCursor;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.database.BaseContract;
import com.bloc.blocspot.model.Category;

/**
 * Created by Wayne on 11/28/2014.
 */
public class CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, TextWatcher, AdapterView.OnItemClickListener {
    private static String keyCategory = "keyCategory";
    private ListView mListView;
    private CategoryViewAdapterCursor mAdapter;
    private static String mCategory;
    private TextView mTextView;
    private ImageView mImageView;
    private Context context;
    public String mNewCategory;
    private EditText editTextFilter;
    private String mCategoryName;


    public static CategoryFragment newInstance(Category selectedCategory) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        //the fist argument is to identify the arg to find it later
        //this is correct way
        //args.putSerializable(keyCategory, selectedCategory);
        fragment.setArguments(args);

        return fragment;
    }

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R
                .layout.fragment_category, container, false);

        //mListView = (ListView) rootView.findViewById(R.id.categoriesListView);
        //String[] places = getResources().getStringArray(R.array.places);
        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, places);
        //listView.setAdapter(adapter);

        mAdapter = new CategoryViewAdapterCursor(getActivity(), 0, null, new String[]{}, new int[]{});

        mListView = (ListView) rootView.findViewById(R.id.categoriesListView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        //mImageView = (ImageView) rootView.findViewById(R.id.imageView_add_cat);
        //mImageView.setOnClickListener(this);

        mTextView = (TextView) rootView.findViewById(R.id.textViewCategoryName);

        editTextFilter = (EditText)rootView.findViewById(R.id.editText_filter);
        editTextFilter.setFocusable(true);
        editTextFilter.requestFocus();
        editTextFilter.addTextChangedListener(this);


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


        if(!editTextFilter.getText().toString().isEmpty()){
            String selection = BaseContract.CategoriesEntry.FRIENDLY_NAME + " LIKE '%' || ? || '%'";

            //Place selectedPlace = (Place) getArguments().getSerializable(keyPlace);

            String[] selectionArgs = new String[]{editTextFilter.getText().toString()};//only here we call get name, here the data been loaded

            return new CursorLoader(
                    getActivity(),   // Parent activity context
                    BaseContract.CategoriesEntry.URI,        // Table to query
                    null,     // Projection to return
                    selection,            // CategoriesEntry.Reference data...actual notebook reference here
                    selectionArgs,        // selectedCategory name
                    BaseContract.CategoriesEntry.FRIENDLY_NAME// Default sort order//sorting by name
            );
        }else{
                    return new CursorLoader(
                    getActivity(),   // Parent activity context
                    BaseContract.CategoriesEntry.URI,        // Table to query
                    null,     // Projection to return
                    null,            // CategoriesEntry.Reference data...actual notebook reference here
                    null,            // selectedCategory name
                    BaseContract.CategoriesEntry.FRIENDLY_NAME// Default sort order//sorting by name
            );
        }
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

    /*
    @Override
    public void onClick(View view) {
        // go to a custom dialog fragment which extends dialogfragment
        FragmentManager manager = getFragmentManager();
        NewCategoryDialogFragment newCategoryDialogFragment = NewCategoryDialogFragment.newInstance();

        newCategoryDialogFragment.show(manager, "NewCategory");
    }
    */
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Category category = (Category)mAdapter.getItem(position);
        mCategoryName = category.getGoogle_name();
        Log.e("Test", "googleName = " + mCategoryName);
        //((BlocSpotActivity)getActivity()).mSearchFragment.loadPlacesResult(mCategoryName);
        ((BlocSpotActivity)getActivity()).loadPlacesResult(mCategoryName);
        //((BlocSpotActivity)getActivity()).removeFragments();
    }
}
