package com.bloc.blocnotes;

import android.app.Activity;
import android.app.DialogFragment;
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
        //setFont = (Button) view.findViewById(R.id.setfont);
        //setStyle = (Button) view.findViewById(R.id.setstyle);
        //setTheme = (Button) view.findViewById(R.id.settheme);
        //setStyle.setOnClickListener(this);
        //setTheme.setOnClickListener(this);
        //setFont.setOnClickListener(this);
        //setCancelable(false);
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
        Toast.makeText(getActivity(),"You selected "+myText.getText(),Toast.LENGTH_LONG).show();
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