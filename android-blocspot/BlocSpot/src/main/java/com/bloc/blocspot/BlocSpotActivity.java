package com.bloc.blocspot;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.blocspot.SearchFragment;
import com.bloc.blocspot.fragments.FavCategoryFragment;
import com.bloc.blocspot.fragments.FavoritePlacesListFragment;
import com.bloc.blocspot.fragments.MarkerDialogFragment;
import com.bloc.blocspot.fragments.PlacesListFragment;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;
import com.bloc.blocspot.utilities.Message;
import com.bloc.blocspot.utilities.Utilities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  This class is used to search places using Places API using keywords like police,hospital etc.
 *
 * @author Karn Shah
 * @Date   10/3/2013
 *
 */
public class BlocSpotActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    public static GoogleMap mMap;
    //private String[] places = {"Restaurant", "Hospital", "ATM", "Bank", "Pet_store", "Police", "Shopping Mall"};
    private LocationManager locationManager;
    private Location loc;
    private LatLng frameworkSystemLocation = new LatLng(32.715, -117.162);
    //private LatLng currentPosition = new LatLng((loc.getLatitude(),loc.getLongitude());
    private String mPlaceType;
    public static ArrayList<Place> returnedPlaces = new ArrayList<Place>();
    protected static ArrayList<Place> favResult = new ArrayList<Place>();

    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();

    private FrameLayout container;
    private double currentLat;
    private double currentLong;
    private LatLng destPosition;
    protected double destLong;
    protected double destLat;
    public SearchFragment mSearchFragment;
    public FavCategoryFragment mFavCategoryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilities.createInitialDatabase(this);
        //navigateFromListView();//test to see if intent/location works
        setUpMapIfNeeded();
        //loadMapsFragment();
        //Message.message(this, "TAG = " +TAG);
        container = (FrameLayout) findViewById(R.id.container);
        final ActionBar actionBar = getActionBar();

        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        /*actionBar.setListNavigationCallbacks(ArrayAdapter.createFromResource(
                        this, R.array.places, android.R.layout.simple_list_item_1),
                new ActionBar.OnNavigationListener() {

                    @Override
                    public boolean onNavigationItemSelected(int itemPosition,
                                                            long itemId) {
                        Log.e(TAG,
                                places[itemPosition].toLowerCase().replace("-",
                                        "_"));
                        if (loc != null) {
                            mMap.clear();
                            new GetPlaces(BlocSpotActivity.this,
                                    places[itemPosition].toLowerCase().replace(
                                            "-", "_").replace(" ", "_")).execute();
                        }
                        return true;
                    }

                });
                */
    }


    private void setUpMap() {
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        // code below sets marker/position to San Diego
        /*
        Marker frameworkSystem = mMap.addMarker(new MarkerOptions()
                .position(frameworkSystemLocation).title("Framework System"));
        frameworkSystem.isVisible();
        //Move the camera to zoom Framework System with 14
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 14));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        //Mark its position on the map
        */
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_list) {
            //swap for list fragment
            loadPlacesListFragment();
            return true;
        }
        if (id == R.id.action_search) {
            //search Google Places API
            //placesSearch();
            loadSearchFragment();
            return true;
        }
        if (id == R.id.action_favorites) {
            //loadFavoritePlacesListFragment();
            loadFavoritePlacesMap();
            //listFavCategories();
        }
        if (id == R.id.action_filter) {
            //filter by category...display categories
            listFavCategories();
        }
        if (id == R.id.action_clear_map) {
            clearMap();
        }
        if (id == R.id.action_settings) {
            //showPrefs is the settings/preferences menu
            //showPrefs();
        }
        return super.onOptionsItemSelected(item);
    }


    private void listFavCategories() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FavCategoryFragment.newInstance())
                .addToBackStack("SearchFragment")
                .commit();

        //FavCategoryDialogFragment favCategoryDialogFragment = new FavCategoryDialogFragment();
        //favCategoryDialogFragment.show(getFragmentManager(), "");
    }

    public void listFavPlaces(String category_name) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FavoritePlacesListFragment.newInstance(category_name))
                .addToBackStack("SearchFragment")
                .commit();
    }

    public void showReturnedPlacesMarkers(ArrayList<Place> result) {
        mMap.clear();
        if (result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {

                    mMap.addMarker(new MarkerOptions()
                            .title(result.get(i).getName())
                            .position(
                                    new LatLng(result.get(i).getLatitude(), result
                                            .get(i).getLongitude()))
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.pin))
                            .snippet(result.get(i).getVicinity()));
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            destPosition = marker.getPosition();
                            destLat = destPosition.latitude;
                            destLong= destPosition.longitude;

                            MarkerDialogFragment markerDialogFragment = new MarkerDialogFragment();
                            Bundle args = new Bundle();
                            args.putString("id", marker.getId());
                            args.putString("place", marker.getTitle());
                            args.putDouble("latitude", destLat);
                            args.putDouble("longitude", destLong);
                            args.putSerializable("vicinity", marker.getSnippet());


                            markerDialogFragment.setArguments(args);
                            markerDialogFragment.show(getFragmentManager(), "");
                        }
                    });

            }


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(result.get(0).getLatitude(), result
                                .get(0).getLongitude())) // Sets the center of the map to
                                // Mountain View
                        .zoom(12) // Sets the zoom
                        .tilt(30) // Sets the tilt of the camera to 30 degrees
                        .build(); // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));

        } else {
            Message.message(this, "No places with that name found");
        }
        this.removeFragments();
    }



    public ArrayList clearMap() {
        if (loc != null) {
            mMap.clear();
            ArrayList<Place> returnedPlaces = new ArrayList<Place>();
        }
        return returnedPlaces;
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    public void navigateFromListView(double destLat, double destLong) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, listener);
        } else {
            loc = location;
            currentLat = loc.getLatitude();
            currentLong = loc.getLongitude();
            //new GetPlaces(BlocSpotActivity.this, places[0].toLowerCase().replace(
            //"-", "_")).execute();
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?"
                    + "saddr=" + currentLat + "," + currentLong + "&daddr=" + destLat + "," + destLong));
            intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Log.e(TAG, "location : " + location);
            Log.e(TAG, "lat/long : " + currentLat + " " + currentLong);
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
            Log.e(TAG, "location update : " + location);
            loc = location;
            locationManager.removeUpdates(listener);
        }
    };

    private void loadFavoritePlacesMap() {
        PlacesDao placesDao = new PlacesDao(this);
        favResult = placesDao.getAllPlaces();
        int icon = R.drawable.ic_favorite_default;
        String imageName = "ic_favorite_yellow.png";
        //int icon = getResources().getIdentifier("drawable/" + imageName, null, getPackageName());
        if (favResult.size() > 0) {
            for (int i = 0; i < favResult.size(); i++) {
                mMap.addMarker(new MarkerOptions()
                        .title(favResult.get(i).getName())
                        .position(
                                new LatLng(favResult.get(i).getLatitude(), favResult
                                        .get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(icon))
                        .snippet(favResult.get(i).getVicinity()));
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        destPosition = marker.getPosition();
                        destLat = destPosition.latitude;
                        destLong= destPosition.longitude;

                        MarkerDialogFragment markerDialogFragment = new MarkerDialogFragment();
                        Bundle args = new Bundle();
                        args.putString("id", marker.getId());
                        args.putString("place", marker.getTitle());
                        args.putDouble("latitude", destLat);
                        args.putDouble("longitude", destLong);
                        args.putSerializable("vicinity", marker.getSnippet());
                        markerDialogFragment.setArguments(args);
                        markerDialogFragment.show(getFragmentManager(), "");
                    }
                });
            }

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(favResult.get(0).getLatitude(), favResult
                            .get(0).getLongitude())) // Sets the center of the map to
                            // Mountain View
                    .zoom(12) // Sets the zoom
                    .tilt(30) // Sets the tilt of the camera to 30 degrees
                    .build(); // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        } else {
            Message.message(getBaseContext(), "No places with that name found");
        }

    }


    private void loadFavoritePlacesListFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, FavoritePlacesListFragment.newInstance(null))
                .addToBackStack("FavoriteCategoriesListFragment")
                .commit();
    }


    private void loadPlacesListFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, PlacesListFragment.newInstance(null))
                .addToBackStack("ListFragment")
                .commit();
    }

    private void loadSearchFragment(){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.container, mSearchFragment = SearchFragment.newInstance())
                .addToBackStack("SearchFragment")
                .commit();
    }

    public void share(String title, double destLat, double destLong){
        title=title.replace(" ","+");
        String request = "https://www.google.com/maps/place/"+title+"/@"+destLat+","+destLong;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this place!");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(request));
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void removeFragments() {
        container.removeAllViews();
    }
}