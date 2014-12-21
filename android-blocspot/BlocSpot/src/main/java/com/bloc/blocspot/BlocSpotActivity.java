package com.bloc.blocspot;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.fragments.AllFavoritePlacesListFragment;
import com.bloc.blocspot.fragments.CategoryFragment;
import com.bloc.blocspot.fragments.FavCategoryFragment;
import com.bloc.blocspot.fragments.FavoritePlacesListFragment;
import com.bloc.blocspot.fragments.MarkerDialogFragment;
import com.bloc.blocspot.fragments.PlacesListFragment;
import com.bloc.blocspot.fragments.SearchFragment;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;
import com.bloc.blocspot.utilities.Message;
import com.bloc.blocspot.utilities.Utilities;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//import android.location.LocationListener;

/**
 *  This class is used to search places using Places API using keywords like police,hospital etc.
 *
 * @author Karn Shah
 * @Date   10/3/2013
 *
 */
public class BlocSpotActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

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
    private long lastBackPressTime = 0;
    private Toast toast;

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
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;
    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invalidateOptionsMenu();
        setContentView(R.layout.activity_main);
        Utilities.createInitialDatabase(this);
        setUpMapIfNeeded();
        //loadMapsFragment();
        //Message.message(this, "TAG = " +TAG);
        container = (FrameLayout) findViewById(R.id.container);
        //final ActionBar actionBar = getActionBar();
        buildGoogleApiClient();
        mRequestingLocationUpdates = true;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                //setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            //updateUI();
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    private void setUpMap() {
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        stopLocationUpdates();
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
            //listAllFavPlaces();
            return true;
        }
        if (id == R.id.action_search) {
            //search Google Places API
            //placesSearch();
            //loadSearchFragment();
            loadCategoryFragment();
            return true;
        }
        if (id == R.id.action_favorites) {
            //loadFavoritePlacesListFragment();
            loadFavoritePlacesMap();
            //listFavCategories();
        }
        if (id == R.id.action_fav_list) {
            //loadFavoritePlacesListFragment();
            listAllFavPlaces();
            //listFavCategories();
        }
        if (id == R.id.action_filter) {
            //filter by category...display categories
            listFavCategories();
        }
        if (id == R.id.action_clear_map) {
            mMap.clear();
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

    public void listAllFavPlaces() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, AllFavoritePlacesListFragment.newInstance())
                .addToBackStack("SearchFragment")
                .commit();
    }



    public void showReturnedPlacesMarkers(ArrayList<Place> result) {
        returnedPlaces = result;
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

        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            //updateUI();
        } else {
            loc = mCurrentLocation;
            currentLat = loc.getLatitude();
            currentLong = loc.getLongitude();
            //new GetPlaces(BlocSpotActivity.this, places[0].toLowerCase().replace(
            //"-", "_")).execute();
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?"
                    + "saddr=" + currentLat + "," + currentLong + "&daddr=" + destLat + "," + destLong));
            intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            Log.e(TAG, "location : " + mCurrentLocation);
            Log.e(TAG, "lat/long : " + currentLat + " " + currentLong);
        }
    }

    public String CalculationByDistance(double lat2, double lon2) {
        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(provider);
        double lat1 = location.getLatitude();
        double lon1 = location.getLongitude();
        int Radius=6371;//radius of earth in Km
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);
        double miles = valueResult*.621371;
        DecimalFormat Mileage = new DecimalFormat("#0.0 mi");
        String mileage = Mileage.format(miles);
        Log.i("Distance in miles", "" + (valueResult*.621371));
        //return Radius * c;
        return mileage;
    }

/*
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
    */

    private void loadFavoritePlacesMap() {
        this.removeFragments();
        PlacesDao placesDao = new PlacesDao(this);
        favResult = placesDao.getAllPlaces();
        mMap.clear();
        if (favResult.size() > 0) {
            for (int i = 0; i < favResult.size(); i++) {
                int currentVisitedValue = favResult.get(i).getVisited();
                //String currentColor = favResult.get(i).getColor();
                mMap.addMarker(new MarkerOptions()
                        .title(favResult.get(i).getName())
                        .position(
                                new LatLng(favResult.get(i).getLatitude(), favResult
                                        .get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(Utilities.getIntColor(favResult.get(i).getColor(), currentVisitedValue)))
                //.icon(BitmapDescriptorFactory
                                //.fromResource(R.drawable.ic_favorite_default))
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

    public void moveCameraToMarker(Place currentListItem) {

        removeFragments();
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .title(currentListItem.getName())
                .position(
                        new LatLng(currentListItem.getLatitude(), currentListItem.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        //.icon(BitmapDescriptorFactory
                        //.fromResource(R.drawable.ic_favorite_default))
                .snippet(currentListItem.getVicinity()));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                destPosition = marker.getPosition();
                destLat = destPosition.latitude;
                destLong = destPosition.longitude;

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
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(currentListItem.getLatitude(), currentListItem.getLongitude()))
                        // Sets the center of the map to selected Place
                .zoom(12) // Sets the zoom
                .tilt(30) // Sets the tilt of the camera to 30 degrees
                .build(); // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    private void loadPlacesListFragment() {
        if (!returnedPlaces.isEmpty()) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.container, PlacesListFragment.newInstance(null, returnedPlaces))
                    .addToBackStack("ListFragment")
                    .commit();
        } else Message.message(this, "Search for some places first");
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

    private void loadCategoryFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, CategoryFragment.newInstance(null))
                .addToBackStack("CategoryFragment")
                .commit();
    }

    public void loadPlacesResult(String google_name) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, PlacesListFragment.newInstance(google_name, new ArrayList<Place>()))
                .addToBackStack("ListFragment")
                .commit();
    }

    public void removeFragments() {
        container.removeAllViews();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0 ){
             super.onBackPressed();
        } else {
              if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
                toast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_LONG);
                toast.show();
                this.lastBackPressTime = System.currentTimeMillis();
              } else {
                if (toast != null) {
                toast.cancel();
              }
              super.onBackPressed();
             }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            //updateUI();
        }

        // If the user presses the Start Updates button before GoogleApiClient connects, we set
        // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
        // the value of mRequestingLocationUpdates and if it is true, we start location updates.
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    /**
     * Callback that fires when the location changes.
     */

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        //updateUI();
        //Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                //Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    /*
    * Called by Google Play services if the connection to GoogleApiClient drops because of an
    * error.
    */
    public void onDisconnected() {
        Log.i(TAG, "Disconnected");
    }

    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }
}