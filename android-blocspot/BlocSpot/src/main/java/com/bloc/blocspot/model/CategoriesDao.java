package com.bloc.blocspot.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bloc.blocspot.database.BaseContract;

import java.util.ArrayList;

/**
 * Created by Wayne on 11/28/2014.
 */
public class CategoriesDao {
    private Context context;

    public CategoriesDao(Context context){ //initializing context
        this.context = context;
    }

    public void insert(Category category){ //now we create a insert for a place object
        ContentValues values = new ContentValues();

        values.put(BaseContract.CategoriesEntry.FRIENDLY_NAME, category.getFriendly_name());//we're linking name of tables to object represent
        values.put(BaseContract.CategoriesEntry.GOOGLE_NAME, category.getGoogle_name());
        values.put(BaseContract.CategoriesEntry.FAVORITE, category.getFavorite());
        values.put(BaseContract.CategoriesEntry.COLOR, category.getColor());
        values.put(BaseContract.CategoriesEntry.MARKER_ICON, category.getMarker_icon());

        ContentUris.parseId(context.getContentResolver().insert(BaseContract.CategoriesEntry.URI, values)); //uri identifies our table and put values in it
    }

    public void update(Category category) {
        ContentValues values = new ContentValues();
        values.put(BaseContract.CategoriesEntry.FRIENDLY_NAME, category.getFriendly_name());
        values.put(BaseContract.CategoriesEntry.FAVORITE, category.getFavorite());
        values.put(BaseContract.CategoriesEntry.COLOR, category.getColor());
        values.put(BaseContract.CategoriesEntry.MARKER_ICON, category.getMarker_icon());
        //to update a name of a category we use a where clause and _ID to find it in the com.bloc.blocspot.database
        String selection = BaseContract.CategoriesEntry._ID + " = ? ";
        String[] selectionArgs = new String[]{ String.valueOf(category.getId())};
        context.getContentResolver().update(BaseContract.CategoriesEntry.URI, values, selection, selectionArgs);
    }

    public void delete(Category category){
        context.getContentResolver().delete(BaseContract.CategoriesEntry.URI, BaseContract.CategoriesEntry._ID + " = " + category.getId(), null);
    }

    public ArrayList<Category> getAllFavCategories(){
        Cursor cursor = context.getContentResolver().query(BaseContract.CategoriesEntry.URI,
                null,
                BaseContract.CategoriesEntry.FAVORITE + " = 'Y'",
                null,//all
                BaseContract.CategoriesEntry.FRIENDLY_NAME);//sort by name

        ArrayList<Category> list = new ArrayList<Category>();

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry._ID)));//this id is generated automatically,
                category.setFriendly_name(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FRIENDLY_NAME)));
                category.setGoogle_name(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.GOOGLE_NAME)));
                category.setFavorite(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FAVORITE)));
                category.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.COLOR)));
                category.setMarker_icon(cursor.getInt(cursor.getColumnIndex(BaseContract.CategoriesEntry.MARKER_ICON)));

                list.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<Category> getAllCategories(){
        Cursor cursor = context.getContentResolver().query(BaseContract.CategoriesEntry.URI,
                null,
                null,//all
                null,//all
                BaseContract.CategoriesEntry.FRIENDLY_NAME);//sort by name

        ArrayList<Category> list = new ArrayList<Category>();

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry._ID)));//this id is generated automatically,
                category.setFriendly_name(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FRIENDLY_NAME)));
                category.setGoogle_name(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.GOOGLE_NAME)));
                category.setFavorite(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FAVORITE)));
                category.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.COLOR)));
                category.setMarker_icon(cursor.getInt(cursor.getColumnIndex(BaseContract.CategoriesEntry.MARKER_ICON)));

                list.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Category getCategory(long id){
        String selection = BaseContract.CategoriesEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Category category = new Category();

        Cursor cursor = context.getContentResolver().query(BaseContract.CategoriesEntry.URI,
                null,
                selection,//all
                selectionArgs,//all
                null);//this query returns only one

        if(cursor.moveToFirst()){

            //when we load data
            //we need to marks loaded
            category.setId(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry._ID)));//this id is generated automatically,
            category.setFriendly_name(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FRIENDLY_NAME)));
            category.setGoogle_name(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.GOOGLE_NAME)));
            category.setFavorite(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FAVORITE)));
            category.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.COLOR)));
            category.setMarker_icon(cursor.getInt(cursor.getColumnIndex(BaseContract.CategoriesEntry.MARKER_ICON)));



        }
        return category;
    }
}
