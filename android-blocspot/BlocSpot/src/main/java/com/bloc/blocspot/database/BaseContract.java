package com.bloc.blocspot.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Wayne on 11/23/2014.
 */
public class BaseContract {
    private static final Uri AUTHORITY_URI = Uri
            .parse("content://com.bloc.blocspot.database.BaseProvider"); //this uri accesses the provider

            public BaseContract() {
            }

    public static abstract class PlacesEntry implements BaseColumns {

        public static final String TABLE = "places"; //this is the name of table
        public static final String FAVORITE ="favorite"; //to see if we have made it a favorite
        public static final String COLOR="color";//color for icon/marker
        public static final String FAV_CATEGORY="category";//custom category for favorites
        public static final String GOOGLEID = "id"; //this is Google Places ID
        public static final String ICON = "icon";//this is Google http address of icon
        public static final String VISITED = "visited";//drawable marker for place
        public static final String NAME = "name";//name of place
        public static final String DESCRIPTION = "description"; //description/notes of place
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String VICINITY = "vicinity";
        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE); //this is how the provider identifies our table
    }

    public static abstract class CategoriesEntry implements BaseColumns {

        public static final String TABLE = "categories"; //this is the name of table
        public static final String FRIENDLY_NAME = "name";//user's name of place
        public static final String GOOGLE_NAME = "search_name"; //google's API type
        public static final String FAVORITE ="favorite"; //to see if we have made it a favorite
        public static final String COLOR = "category_color"; //color associated with category
        public static final String MARKER_ICON = "marker_icon";//marker icon associated with category
        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE); //this is how the provider identifies our table
    }

    public static abstract class FavoriteCategoriesEntry implements BaseColumns {

        public static final String TABLE = "favorite_categories"; //this is the name of table
        public static final String FAV_NAME = "name";//user's name of category
        public static final String COLOR = "category_color"; //color associated with category
        public static final String ICON = "marker_icon";//marker icon associated with category

        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE); //this is how the provider identifies our table
    }
}