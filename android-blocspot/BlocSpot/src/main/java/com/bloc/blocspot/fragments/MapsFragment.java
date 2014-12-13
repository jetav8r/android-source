package com.bloc.blocspot.fragments;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.model.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Wayne on 11/24/2014.
 */
public class MapsFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private String[] places;
    private LocationManager locationManager;
    private Location loc;
    private LatLng frameworkSystemLocation = new LatLng(32.715,-117.162);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R
                .layout.fragment_map, container, false);
        //places = getResources().getStringArray(R.array.places);
        //initMap();

        return rootView;
    }

    public static MapsFragment newInstance(Place selectedPlace) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        //the first argument is to identify the arg to find it later
        //this is correct way
        //args.putSerializable(keyPlace, selectedPlace);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_map).setVisible(false);
        menu.findItem(R.id.action_list).setVisible(true);
    }

    /*
    private void initMap() {
        mMap = ((com.google.android.gms.maps.MapsFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker frameworkSystem = mMap.addMarker(new MarkerOptions()
                .position(frameworkSystemLocation).title("Framework System"));
        frameworkSystem.isVisible();
        // Move the camera to zoom Framework System with 15
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        //Mark its position on the map
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
    }
        */

}
