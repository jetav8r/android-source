package com.bloc.blocnotes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bloc.blocnotes.BlocNotes;
import com.bloc.blocnotes.Message;
import com.bloc.blocnotes.R;
import com.bloc.blocnotes.model.Note;
import com.bloc.blocnotes.model.NotesDao;
import com.bloc.blocnotes.util.Utilities;

/**
 * Created by Wayne on 11/5/2014.
 */
public class CreateNoteFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_NOTEBOOK_NAME = "notebook_name";
    private EditText editText;
    private Button buttonSave;
    private String noteName;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CreateNoteFragment newInstance(String notebookName) {
        CreateNoteFragment fragment = new CreateNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOTEBOOK_NAME, notebookName);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bloc_notes, container, false);

        editText = (EditText)rootView.findViewById(R.id.text);
        Utilities.restoreFontType(getActivity(), editText);
        Utilities.restoreFontSize(getActivity(), editText);

        buttonSave = (Button)rootView.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BlocNotes) activity).onSectionAttached(
                getArguments().getInt(ARG_NOTEBOOK_NAME));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == buttonSave.getId()){
            NotesDao notesDao = new NotesDao(getActivity());
            Note note = new Note();
            note.setBody(editText.getText().toString());
            noteName = getArguments().getString(ARG_NOTEBOOK_NAME);
            if (noteName==""){
                noteName = "Uncategorized";
                note.setReference(noteName);
            } else {
                note.setReference(noteName);
            }
            notesDao.insert(note);
            editText.setText("");
            editText.requestFocus();
                        Message.message(getActivity(), "Note was saved succesfully to " +noteName + " notebook");
        }
    }
}