package com.bloc.blocspot.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.model.CategoriesDao;
import com.bloc.blocspot.model.Category;
import com.bloc.blocspot.utilities.Message;


/**
 * Created by Wayne on 11/29/2014.
 */
public class NewCategoryDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    protected String mNewCategory;
    private Context mContext;
    private EditText userInput;

    public static NewCategoryDialogFragment newInstance() {
        NewCategoryDialogFragment frag = new NewCategoryDialogFragment();
        //Bundle args = new Bundle();
        //args.putInt("New Category", title);
        //frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //int title = getArguments().getInt("New Category");
        userInput = new EditText(getActivity());
        return new AlertDialog.Builder(getActivity())

                .setTitle("New Category")
                .setView(userInput)
                .setMessage("Category Name?")
                .setNeutralButton("OK", this).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        newCategory(userInput.getText().toString());
    }

    public void newCategory(String category) {
        mNewCategory = category;
        Log.e("Wayne", "mNewCategory = " + mNewCategory);
        Category newCategory= new Category();
        newCategory.setFriendly_name(mNewCategory);
        CategoriesDao categoriesDao = new CategoriesDao(getActivity());
        categoriesDao.insert(newCategory);
    }

}

