package com.bloc.blocspot.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wayne on 11/23/2014.
 */
public class BlocSpotHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "blocspot";
    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    private static final String DEFAULT_VALUE = " DEFAULT ";

    public static final String CREATE_PLACES = "CREATE TABLE " + BaseContract.PlacesEntry.TABLE + " (" +
            //BaseContract.PlacesEntry._ID + INTEGER_PRIMARY_KEY + ");";
            BaseContract.PlacesEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
            BaseContract.PlacesEntry.GOOGLEID + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.FAVORITE + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.COLOR + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.FAV_CATEGORY + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.NAME + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.MARKER_ICON + INTEGER_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.ICON + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.LATITUDE + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.LONGITUDE + TEXT_TYPE + COMMA_SEP +
            BaseContract.PlacesEntry.VICINITY + TEXT_TYPE + " UNIQUE  " + COMMA_SEP +
            BaseContract.PlacesEntry.DESCRIPTION + TEXT_TYPE + " );";

    public static final String CREATE_CATEGORIES = "CREATE TABLE " + BaseContract.CategoriesEntry.TABLE + " (" +
            BaseContract.CategoriesEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
            BaseContract.CategoriesEntry.FRIENDLY_NAME + TEXT_TYPE + COMMA_SEP +
            BaseContract.CategoriesEntry.GOOGLE_NAME+ TEXT_TYPE + COMMA_SEP +
            BaseContract.CategoriesEntry.FAVORITE + TEXT_TYPE + COMMA_SEP +
            BaseContract.CategoriesEntry.COLOR + TEXT_TYPE + COMMA_SEP +
            BaseContract.CategoriesEntry.MARKER_ICON+ INTEGER_TYPE + " );";

    public static final String CREATE_FAV_CATEGORIES = "CREATE TABLE " + BaseContract.FavoriteCategoriesEntry.TABLE + " (" +
            BaseContract.FavoriteCategoriesEntry._ID + INTEGER_PRIMARY_KEY + COMMA_SEP +
            BaseContract.FavoriteCategoriesEntry.FAV_NAME + TEXT_TYPE + " UNIQUE  " + COMMA_SEP +
            BaseContract.FavoriteCategoriesEntry.COLOR + TEXT_TYPE + COMMA_SEP +
            BaseContract.FavoriteCategoriesEntry.ICON+ TEXT_TYPE + " );";

    public BlocSpotHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //Message.message(context, "db constructor was called");
    }

    @Override
    public void onCreate(SQLiteDatabase mSqliteDatabase) {

        try {
            mSqliteDatabase.execSQL(CREATE_PLACES);
            mSqliteDatabase.execSQL(CREATE_CATEGORIES);
            mSqliteDatabase.execSQL(CREATE_FAV_CATEGORIES);
            //Message.message(context, "onCreate was called");
        } catch (SQLException e) {
            //Message.message(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP_TABLE_IF_EXISTS" + CREATE_PLACES);
        sqLiteDatabase.execSQL("DROP_TABLE_IF_EXISTS" + CREATE_CATEGORIES);
        sqLiteDatabase.execSQL("DROP_TABLE_IF_EXISTS" + CREATE_FAV_CATEGORIES);

        onCreate(sqLiteDatabase);

        /*if(oldVersion < 2){//upgrading to 2
            try{
                //we are adding a new column if the user already has the app installed. We do not
                //delete it, we upgrade it by Altering the table instead of destroying and recreating
                //sqLiteDatabase.execSQL("ALTER TABLE " + BaseContract.PlacesEntry.TABLE + " ADD COLUMN "
                //        + BaseContract.PlacesEntry.(PUT NEW COLUMN NAME HERE) + TEXT_TYPE + ";");
                Log.e("Wayne", "" + "upgraded");
            }catch (SQLException e){
                Log.e("Wayne","" + e.getMessage());
            }
        }
        */
    }
}
