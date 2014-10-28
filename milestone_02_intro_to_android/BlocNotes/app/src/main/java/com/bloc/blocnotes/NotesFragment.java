package com.bloc.blocnotes;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bloc.blocnotes.adapters.ListViewAdapterCursor;
import com.bloc.blocnotes.bd.BaseContract;
import com.bloc.blocnotes.bd.Notebook;

/**
 * Created by Wayne on 10/27/2014.
 */
public class NotesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static String selectedNotebook = "selectedNotebook";
    private ListView listView;
    private ListViewAdapterCursor mAdapter;

    public static NotesFragment newInstance(Notebook notebook) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putSerializable(selectedNotebook, notebook);
        fragment.setArguments(args);

        return fragment;
    }

    public NotesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        Message.message(getActivity(), "here ok");

        mAdapter = new ListViewAdapterCursor(getActivity(), 0, null, new String[]{}, new int[]{});

        getLoaderManager().initLoader(0, null, this);

        listView = (ListView)rootView.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String selection = BaseContract.NotesEntry.REFERENCE + " = ?";

        Notebook argumentNotebook = (Notebook)getArguments().getSerializable(selectedNotebook);

        String[] selectionArgs = new String[]{argumentNotebook.getName()};

        return new CursorLoader(
                getActivity(),   // Parent activity context
                BaseContract.NotesEntry.URI,        // Table to query
                null,     // Projection to return
                selection,            // where//all
                selectionArgs,            // Selection args//all
                null// Default sort order//sorting by name
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BlocNotes) activity).onSectionAttached(0);
    }
}
