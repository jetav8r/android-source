package com.bloc.blocnotes;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Wayne on 9/8/2014.
 */
public class CustomStyleDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    int mSpinnerPos;
    //Button setFont, setStyle, setTheme;
    //Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //communicator= (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_style_dialog, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        View view = getView();
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.items,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(getActivity(),"You selected "+myText.getText()+", i = "+i,Toast.LENGTH_LONG).show();
        mSpinnerPos = i;
        if (i==1) {
            //(BlocNotes.onFontChange(pass font to method)) or call separate dialog to select font, then pass it back;
            //also launch font selection submenu/spinner - do the same for the style and theme selections too
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //@Override
    //public void onClick(View view) {
        //if (view.getId()==R.id.setfont) {
            //communicator.onDialogMessage("Set Font was clicked");
            //dismiss();
        //}
        //else if (view.getId()==R.id.setstyle) {
            //communicator.onDialogMessage("Set Style was clicked");

            //dismiss();
        //}
        //else if (view.getId()==R.id.settheme) {
            //communicator.onDialogMessage("Set Theme was clicked");
           // dismiss();
       // }
    //}

    //interface Communicator {
       //public void onDialogMessage(String message);
    //}
}