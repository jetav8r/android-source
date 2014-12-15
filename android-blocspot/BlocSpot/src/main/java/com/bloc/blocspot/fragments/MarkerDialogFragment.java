package com.bloc.blocspot.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;
import com.bloc.blocspot.utilities.Message;

/**
 * Created by Wayne on 12/4/2014.
 */
public class MarkerDialogFragment extends DialogFragment implements View.OnClickListener {
    AlertDialog.Builder builder;

    public MarkerDialogFragment(){}

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        final String id = getArguments().getString("id");
        final String title = getArguments().getString("place");
        final double destLat = getArguments().getDouble("latitude");
        final double destLong = getArguments().getDouble("longitude");
        final String vicinity = getArguments().getString("vicinity");
        builder.setTitle(title)
                .setItems(getActivity().getResources().getStringArray(R.array.marker_menu), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                addToFavorite(id, title, destLat, destLong, vicinity);
                                //Message.message(getActivity(), "Test marker dialog");
                                break;
                            case 1:
                                navigateTo(destLat, destLong);
                                break;
                            case 2:
                                share(title, destLat, destLong);
                                break;
                            default:
                                break;
                        }
                    }
                });

        Dialog dialog = builder.create();

        return dialog;
    }

    private void addToFavorite(String id, String title, double destLat, double destLong, String vicinity){
        PlacesDao placesDao = new PlacesDao(getActivity());
        Place place = new Place();
        place.setFavorite("Y");
        place.setGoogleId(id);
        place.setName(title);
        place.setLatitude(destLat);
        place.setLongitude(destLong);
        place.setVicinity(vicinity);
        place.setColor("#40c4ff");
        place.setVisited(0);
        try {
            placesDao.insert(place);
            Message.message(getActivity(), "Favorite added");
        } catch (Exception e) {
            Message.message(getActivity(), "Favorite already added");
        }
    }

    private void navigateTo(double destLat, double destLong){
        ((BlocSpotActivity) getActivity()).navigateFromListView(destLat, destLong);

    }

    private void share(String title, double destLat, double destLong){
        String request = "https://www.google.com/maps/place/"+title+"/@"+destLat+","+destLong;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this place!");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(request));
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

}
