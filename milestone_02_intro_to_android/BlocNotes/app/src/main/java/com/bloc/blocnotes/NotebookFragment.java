package com.bloc.blocnotes;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.bloc.blocnotes.adapters.ListViewAdapter;
import com.bloc.blocnotes.adapters.ListViewAdapterCursor;
import com.bloc.blocnotes.bd.BaseContract;
import com.bloc.blocnotes.bd.BlocNotesHelper;
import com.bloc.blocnotes.bd.Note;
import com.bloc.blocnotes.bd.NotesDao;

import java.util.List;

/**
 * Created by Wayne on 10/24/2014.
 */
//public class NotebookFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
public class NotebookFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    static String ARG_COLUMN2="column_2";
    static String ARG_COLUMN = "column";
    static String ARG_TABLE_NAME = "table";

    public static String mNewNote;
    private ListViewAdapterCursor mListViewAdapterCursor;
    private ListView mNoteListView;

    // The desired columns to be bound
    //String[] columns = {BlocNotesHelper.UID, BlocNotesHelper.reference, BlocNotesHelper.body};
    // the XML defined views which the data will be bound to
    //int[] to = new int[] {android.R.id.text1};
    //String nbRef = "To Do";
    //String nbRef = (String) mTitle;
    //BlocNotesHelper blocNotesHelper = new BlocNotesHelper(getActivity());
    //SQLiteDatabase sqLiteDatabase = blocNotesHelper.getWritableDatabase();
    //Cursor cursor = sqLiteDatabase.query(BlocNotesHelper.TABLE2_NAME,columns,BlocNotesHelper.reference+" = '"+nbRef+"'",null,null,null,null);
    //SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_notebook, cursor, columns, to);
    //ListView listView = (ListView) findViewById(R.id.listView);
    //listView.setAdapter(dataAdapter);

    public void insertNewNote() {
        Note note = new Note();
        note.setBody(mNewNote);

        NotesDao notesDao = new NotesDao(getActivity());
        notesDao.insert(note);

        Message.message(getActivity(), "inserted");
    }
    public static NotebookFragment newInstance(String column_2,String column, String table) {
        NotebookFragment fragment = new NotebookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COLUMN2,column_2);
        args.putString(ARG_COLUMN, column);
        args.putString(ARG_TABLE_NAME, table);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Message.message(getActivity(),"Notebook Fragment called");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notebook,container,false);
        view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));

        //Button button = (Button) view.findViewById(R.id.note_button);
        //button.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
             //   Message.message(getActivity(),"Button was clicked");
            //}
        //});

        //first we create a cursor null with no data
        mListViewAdapterCursor = new ListViewAdapterCursor(getActivity(), 0, null, new String[]{}, new int[]{});

        //we need to initiate loader
        getLoaderManager().initLoader(0, null, this);
        mNoteListView = (ListView)view.findViewById(R.id.mnotelist);
        mNoteListView.setAdapter(mListViewAdapterCursor);
        return mNoteListView;
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),   // Parent activity context
                BaseContract.NotesEntry.URI,        // Table to query
                null,     // Projection to return
                null,            // where//all
                null,            // Selection args//all
                BaseContract.NotesEntry.BODY// Default sort order//sorting by name
        );
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> cursorLoader, Cursor cursor) {
        //when load finish, set cursor to adapter
        mListViewAdapterCursor.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> cursorLoader) {
        //when load reset, set null cursor
        mListViewAdapterCursor.swapCursor(null);
    }//@Override
   // public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        //String nbRef = (name from navigation drawer)
        //String selection = BlocNotesHelper.reference+ " = ?";
        //String selectionArgs[] = new String[]{getArguments().getString(ARG_COLUMN)};

        //return new CursorLoader(
                 //getActivity(),   // Parent activity context
                //BaseDadosContract.PoupancaEntry.URI,        // Table to query
                //null,     // Projection to return
                //selection,            // where
                //selectionArgs,            // Selection args
                //BaseDadosContract.PoupancaEntry.NOME// Default sort order
        //);

    //}
        //return null;
    //}

    //@Override
    //public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    //}

    //@Override
    //public void onLoaderReset(Loader<Cursor> cursorLoader) {

    //}
}
