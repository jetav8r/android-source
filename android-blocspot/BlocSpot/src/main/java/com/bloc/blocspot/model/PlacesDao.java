package com.bloc.blocspot.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bloc.blocspot.database.BaseContract;

import java.util.ArrayList;

/**
 * Created by Wayne on 11/23/2014.
 */
public class PlacesDao {
    private Context context;

    public PlacesDao(Context context){ //initializing context
        this.context = context;
    }

    public void insert(Place place){ //now we create a insert for a place object
        ContentValues values = new ContentValues();

        values.put(BaseContract.PlacesEntry.NAME, place.getName());
        values.put(BaseContract.PlacesEntry.FAVORITE, place.getFavorite());
        values.put(BaseContract.PlacesEntry.FAV_CATEGORY, place.getFav_Category());
        values.put(BaseContract.PlacesEntry.COLOR, place.getColor());
        values.put(BaseContract.PlacesEntry.GOOGLEID, place.getId());
        values.put(BaseContract.PlacesEntry.DESCRIPTION, place.getDescription());
        values.put(BaseContract.PlacesEntry.ICON, place.getIcon());
        values.put(BaseContract.PlacesEntry.VISITED, place.getVisited());
        values.put(BaseContract.PlacesEntry.LATITUDE, place.getLatitude());
        values.put(BaseContract.PlacesEntry.LONGITUDE, place.getLongitude());
        values.put(BaseContract.PlacesEntry.VICINITY, place.getVicinity());


        ContentUris.parseId(context.getContentResolver().insert(BaseContract.PlacesEntry.URI, values)); //uri identifies our table and put values in it
    }

    public void update(Place place) {
        ContentValues values = new ContentValues();
        values.put(BaseContract.PlacesEntry.DESCRIPTION, place.getDescription());
        values.put(BaseContract.PlacesEntry.FAV_CATEGORY, place.getFav_Category());
        values.put(BaseContract.PlacesEntry.COLOR, place.getColor());
        values.put(BaseContract.PlacesEntry.VISITED, place.getVisited());

        //to update a note we use a where clause and _ID to find it in the com.bloc.blocspot.database
        String selection = BaseContract.PlacesEntry._ID + " = ? ";
        String[] selectionArgs = new String[]{ String.valueOf(place.getId())};
        context.getContentResolver().update(BaseContract.PlacesEntry.URI, values, selection, selectionArgs);
    }

    public void delete(Place place){
        context.getContentResolver().delete(BaseContract.PlacesEntry.URI, BaseContract.PlacesEntry._ID + " = " + place.getId(), null);
    }

    public String[] getArrayStringFavCategories(){
        ArrayList<Place> listPlaces = getAllPlaces();
        String[] arrayStringPlaces = new String[listPlaces.size()];

        for (int i = 0; i < listPlaces.size(); i++){
            arrayStringPlaces[i] = listPlaces.get(i).getFav_Category();
        }
        return arrayStringPlaces;
    }


    public ArrayList<Place> getAllPlaces(){
        Cursor cursor = context.getContentResolver().query(BaseContract.PlacesEntry.URI,
                null,
                null,//all
                null,//all
                BaseContract.PlacesEntry.NAME);//sort by name

        ArrayList<Place> list = new ArrayList<Place>();

        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setId(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry._ID)));//this id is generated automatically,
                place.setFavorite(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.FAVORITE)));
                place.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.COLOR)));
                place.setFav_Category(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.FAV_CATEGORY)));
                place.setGoogleId(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.GOOGLEID)));
                place.setName(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.NAME)));
                place.setVisited(cursor.getInt(cursor.getColumnIndex(BaseContract.PlacesEntry.VISITED)));
                place.setDescription(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.DESCRIPTION)));
                place.setVicinity(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.VICINITY)));
                place.setIcon(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.ICON)));
                place.setLatitude(cursor.getDouble(cursor.getColumnIndex(BaseContract.PlacesEntry.LATITUDE)));
                place.setLongitude(cursor.getDouble(cursor.getColumnIndex(BaseContract.PlacesEntry.LONGITUDE)));
                list.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<Place> getPlacesInCategory(String category_name){
        String selection = BaseContract.PlacesEntry.FAV_CATEGORY + " = ?";
        String[] selectionArgs = new String[]{category_name};
        Cursor cursor = context.getContentResolver().query(BaseContract.PlacesEntry.URI,
                null,
                selection,//all
                selectionArgs,//all
                null);//no sort

        ArrayList<Place> placesInCatList = new ArrayList<Place>();

        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setId(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry._ID)));//this id is generated automatically,
                place.setName(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.NAME)));
                place.setFav_Category(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.FAV_CATEGORY)));
                place.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.COLOR)));
                placesInCatList.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return placesInCatList;
    }

    public Place getPlace(long id){
        String selection = BaseContract.PlacesEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Place place = new Place();

        Cursor cursor = context.getContentResolver().query(BaseContract.PlacesEntry.URI,
                null,
                selection,//all
                selectionArgs,//all
                null);//this query returns only one

        if(cursor.moveToFirst()){

            //when we load data
            //we need to marks loaded
            place.setId(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry._ID)));//this id is generated automatically,
            place.setFavorite(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.FAVORITE)));
            place.setName(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.NAME)));
            place.setGoogleId(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.GOOGLEID)));
            place.setDescription(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.DESCRIPTION)));
            place.setIcon(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.ICON)));
            place.setVisited(cursor.getInt(cursor.getColumnIndex(BaseContract.PlacesEntry.VISITED)));
            place.setVicinity(cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.VICINITY)));
            place.setLatitude(cursor.getDouble(cursor.getColumnIndex(BaseContract.PlacesEntry.LATITUDE)));
            place.setLongitude(cursor.getDouble(cursor.getColumnIndex(BaseContract.PlacesEntry.LONGITUDE)));
        }
        return place;
    }
}
