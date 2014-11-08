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
public class EditNoteFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_NOTE = "note";
    private EditText editText;
    private Button buttonSave;
    private Note note;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public EditNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bloc_notes, container, false);

        //getting the note argument
        //this contains id
        note = (Note)getArguments().getSerializable(ARG_NOTE);

        editText = (EditText)rootView.findViewById(R.id.text);
        //setting the value to edittext
        editText.setText(note.getBody());

        Utilities.restoreFontType(getActivity(), editText);
        Utilities.restoreFontSize(getActivity(), editText);

        buttonSave = (Button)rootView.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == buttonSave.getId()){
            NotesDao notesDao = new NotesDao(getActivity());
            //this not
            //we need only change text of body
            //other data are in there
            note.setBody(editText.getText().toString());
            notesDao.update(note);//we can update now
            editText.setText("");
            Message.message(getActivity(), "Note was saved succesfully");
        }
    }
}