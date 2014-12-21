package com.bloc.blocspot.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.adapters.PlacesListViewAdapter;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.services.PlacesService;

import java.util.ArrayList;

/**
 * Created by Wayne on 12/1/2014.
 */
public class PlacesListFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private Context context;
    private ImageView mImageView;
    private LocationManager locationManager;
    private Location loc;
    private static String ARG_GOOGLE_NAME = "google_name";
    private static String ARG_PLACES_PASSED = "places_passed";

    ArrayList<Place> places = new ArrayList<Place>();


    public static PlacesListFragment newInstance(String  google_name, ArrayList<Place> places_passed) {
        PlacesListFragment fragment = new PlacesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GOOGLE_NAME, google_name);
        args.putSerializable(ARG_PLACES_PASSED, places_passed);
        //the first argument is to identify the arg to find it later
        //args.putSerializable(keyPlace, selectedPlace);
        fragment.setArguments(args);

        return fragment;
    }
    public PlacesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R
                .layout.fragment_places_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.returnedPlaceslistView);

        places = (ArrayList<Place>)getArguments().getSerializable(ARG_PLACES_PASSED);

        if(!places.isEmpty()){
            PlacesListViewAdapter adapter = new PlacesListViewAdapter(getActivity(), places);
            listView.setAdapter(adapter);
        }else{
            //call search here
            String google_name = getArguments().getString(ARG_GOOGLE_NAME);
            currentLocation(google_name);
        }


        //new GetPlaces(getActivity(), google_name).execute();



        listView.setOnItemClickListener(this);


/*
        // list adapter
        ListAdapter adapter = new SimpleAdapter(BlocSpotActivity.this, placesListItems,
                R.layout.list_item,
                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                R.id.reference, R.id.name });

        // Adding data into listview
        listView.setAdapter(adapter);

*/

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_for_listplaces, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //menu.findItem(R.id.action_map).setVisible(true);
        //menu.findItem(R.id.action_list).setVisible(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_map){
            setMenuVisibility(false);
            ((BlocSpotActivity) getActivity()).showReturnedPlacesMarkers(places);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int mId = view.getId();
        Place selectedPlace = places.get(position);

        //Message.message(getActivity(), "Selected place = "+selectedPlace);
        ((BlocSpotActivity)getActivity()).moveCameraToMarker(selectedPlace);
    }


    public void currentLocation(String google_name) {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, listener);
        } else {
            loc = location;
            //new GetPlaces(BlocSpotActivity.this, places[0].toLowerCase().replace(
            //"-", "_")).execute();
            new GetPlaces(getActivity(), google_name).execute();
            //Log.e(TAG, "location : " + location);
        }
    }


    private LocationListener listener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //Log.e(TAG, "location update : " + location);
            loc = location;
            locationManager.removeUpdates(listener);
        }
    };

    public class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

        private ProgressDialog dialog;
        private Context context;
        private String google_name;

        public GetPlaces(Context context, String google_name) {
            this.context = context;
            this.google_name = google_name;
        }

        @Override
        public void onPostExecute(final ArrayList<Place> result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            PlacesListViewAdapter adapter = new PlacesListViewAdapter(getActivity(), places);
            listView.setAdapter(adapter);
            //((BlocSpotActivity) getActivity()).showReturnedPlacesMarkers(places);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
        }

        @Override
        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(
                    //"Put your project browser API key here"
                    "AIzaSyBOotwfHcV4BNWNTWw2V-DIU5616-4k0WY");
            ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808
                    loc.getLongitude(), google_name); // 77.218276
            places.clear();

            for (int i = 0; i < findPlaces.size(); i++) {
                Place place = findPlaces.get(i);
                //now try to create ArrayList of places

                //returnedPlaces.add(placeDetail.getName());
                places.add(place);
                /*
                Log.i("result", "places : " + place.getName());
                Log.i(TAG, "vicinity : "+ placeDetail.getVicinity());
                Log.i(TAG, "description : "+ placeDetail.getDescription());
                Log.i(TAG, "icon : "+ placeDetail.getIcon());
                Log.i(TAG, "id : " + placeDetail.getId());
                Log.i(TAG, "latitude : "+placeDetail.getLatitude());
                Log.i(TAG, "longitude : "+placeDetail.getLongitude());
                */
            }

            return findPlaces;
        }
    }
}