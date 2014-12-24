package com.bloc.blocspot.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.bloc.blocspot.model.CategoriesDao;
import com.bloc.blocspot.model.Category;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;

import java.util.ArrayList;

/**
 * Created by Wayne on 12/10/2014.
 */
public class FavCategoryDialogFragment extends DialogFragment implements View.OnClickListener {

    protected ArrayList<String> mFavoriteCategories;

    AlertDialog.Builder builder;
    protected String currentColor = "#40c4ff";

    public void onClick(View view) {
        int id = view.getId();

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        CategoriesDao categoriesDao = new CategoriesDao(getActivity());
        final ArrayList<Category> categoryArrayList = categoriesDao.getAllFavCategories();

        String[] mFavoriteCategories = new String[categoryArrayList.size()];
        if(!categoryArrayList.isEmpty()){
            for (int i = 0; i <  categoryArrayList.size(); i ++) {
                mFavoriteCategories[i] = categoryArrayList.get(i).getFriendly_name();
            }
        }

        builder = new AlertDialog.Builder(getActivity());
        //final String[] mFavoriteCategories = getArguments().getString[]("categories");
        builder.setTitle("Select Category");
        builder.setItems(mFavoriteCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlacesDao placesDao = new PlacesDao(getActivity());
                Place place = (Place) getArguments().getSerializable("keyPlace");
                String currentCategory = categoryArrayList.get(which).getGoogle_name();
                currentColor = categoryArrayList.get(which).getColor();
                place.setVisited(0);
                place.setColor(currentColor);
                place.setFav_Category(currentCategory);
                placesDao.update(place);
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }
}
