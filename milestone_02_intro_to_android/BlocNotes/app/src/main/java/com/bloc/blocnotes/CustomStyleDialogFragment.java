package com.bloc.blocnotes;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.TypedValue;

/**
 * Created by Wayne on 9/8/2014.
 */
public class CustomStyleDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, iCustomStyle{

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //communicator= (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_style_dialog, null);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setPrompt("Custom Style");
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.fonts, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        RadioGroup fonts  = (RadioGroup) view.findViewById(R.id.rg_font_size);

        fonts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.radioButtonSmall) {
                    final EditText text = (EditText) getActivity().findViewById(R.id.text);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
                    //text.setTextSize(12);
                } else if (i==R.id.radioButtonMed) {
                        final EditText text = (EditText) getActivity().findViewById(R.id.text);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_PX, 44);
                    //text.setTextSize(18);
                } else if (i==R.id.radioButtonLarge) {
                        final EditText text = (EditText) getActivity().findViewById(R.id.text);
                        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, 64);
                        //text.setTextSize(24);
                    }
            }
        });


        return view;
    }

        /*
        Button small = (Button) view.findViewById(R.id.small);
        Button medium = (Button) view.findViewById(R.id.medium);
        Button large = (Button) view.findViewById(R.id.large);
        small.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        final EditText text = (EditText) getActivity().findViewById(R.id.text);
        text.setTextSize(12);
        }
        });
        medium.setOnClickListener(new View.OnClickListener() {
        */

        /*
           @Override
           public void onClick(View view) {
           final EditText text = (EditText) getActivity().findViewById(R.id.text);
           text.setTextSize(18);
           }
           });
           large.setOnClickListener(new View.OnClickListener() {
           */

        /*
           @Override
           public void onClick(View view) {
           final EditText text = (EditText) getActivity().findViewById(R.id.text);
           text.setTextSize(24);
           }
           });
           */

        //return view;

    //}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //TextView myText = (TextView) view;
        //Toast.makeText(getActivity(), "You selected " + myText.getText() + ", i = " + i, Toast.LENGTH_LONG).show();

        CustomStyleDialogFragment dialog = new CustomStyleDialogFragment();
        String fontName;
        int position;
        switch (i) {
            case 0:
                fontName = "Default";
                position=1;
                this.onFontChange(dialog, fontName, position);
                break;
            case 1:
                fontName = "Helvetica_Reg.ttf";
                position=2;
                this.onFontChange(dialog, fontName, position);
                break;
            case 2:
                fontName = "HelveticaNeue_Lt.ttf";
                position=3;
                this.onFontChange(dialog, fontName, position);
                break;
            case 3:
                fontName = "impact.ttf";
                position=4;
                this.onFontChange(dialog, fontName, position);
                break;
            case 4:
                fontName = "Serif";
                position=5;
                this.onFontChange(dialog, fontName, position);
                break;
            case 5:
                fontName = "Sans Serif";
                position=6;
                this.onFontChange(dialog, fontName, position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onStyleChange(CustomStyleDialogFragment dialog, int styleId) {

    }


    @Override
    public void onFontChange(CustomStyleDialogFragment dialog, String fontName, int position) {
        //Toast.makeText(getActivity(),"position is " + position ,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "The onFontChange method was called with fontName = " + fontName, Toast.LENGTH_LONG).show();
        EditText text = (EditText) getActivity().findViewById(R.id.text);
        Context context = getActivity();
        SharedPreferences sharedPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("fontName", fontName);
        editor.putInt("position", position);
        editor.commit();

        if (fontName == "Default") {
            text.setTypeface(Typeface.DEFAULT);
        } else {
            if (fontName == "Serif") {
                text.setTypeface(Typeface.SERIF);
            } else {
                if (fontName == "Sans Serif") {
                    text.setTypeface(Typeface.SANS_SERIF);
                } else {
                    Typeface currentFont = Typeface.createFromAsset(getActivity().getAssets(), fontName);
                    text.setTypeface(currentFont);


                    //Toast.makeText(getActivity(), "currentFont variable = " + currentFont, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onThemeChange(CustomStyleDialogFragment dialog, int themeId) {

    }
}
