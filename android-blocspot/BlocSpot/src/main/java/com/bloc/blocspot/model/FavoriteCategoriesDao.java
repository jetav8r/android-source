package com.bloc.blocspot.model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bloc.blocspot.database.BaseContract;

import java.util.ArrayList;

/**
 * Created by Wayne on 12/10/2014.
 */
public class FavoriteCategoriesDao {
    private Context context;

    public FavoriteCategoriesDao(Context context){ //initializing context
        this.context = context;
    }

    public void insert(FavoriteCategory favoriteCategory){ //now we create a insert for a place object
        ContentValues values = new ContentValues();

        values.put(BaseContract.FavoriteCategoriesEntry.FAV_NAME, favoriteCategory.getFav_name());//we're linking name of tables to object represent
        values.put(BaseContract.FavoriteCategoriesEntry.COLOR, favoriteCategory.getColor());
        values.put(BaseContract.FavoriteCategoriesEntry.ICON, favoriteCategory.getIcon());

        ContentUris.parseId(context.getContentResolver().insert(BaseContract.FavoriteCategoriesEntry.URI, values)); //uri identifies our table and put values in it
    }

    public void update(FavoriteCategory favoriteCategory) {
        ContentValues values = new ContentValues();
        values.put(BaseContract.FavoriteCategoriesEntry.FAV_NAME, favoriteCategory.getFav_name());
        values.put(BaseContract.FavoriteCategoriesEntry.COLOR, favoriteCategory.getColor());
        values.put(BaseContract.FavoriteCategoriesEntry.ICON, favoriteCategory.getIcon());

        //to update a name of a category we use a where clause and _ID to find it in the com.bloc.blocspot.database
        String selection = BaseContract.FavoriteCategoriesEntry._ID + " = ? ";
        String[] selectionArgs = new String[]{ String.valueOf(favoriteCategory.getId())};
        context.getContentResolver().update(BaseContract.FavoriteCategoriesEntry.URI, values, selection, selectionArgs);
    }

    public void delete(FavoriteCategory favoriteCategory){
        context.getContentResolver().delete(BaseContract.FavoriteCategoriesEntry.URI, BaseContract.FavoriteCategoriesEntry._ID
                + " = " + favoriteCategory.getId(), null);
    }


    public ArrayList<FavoriteCategory> getAllFavoriteCategories(){
        Cursor cursor = context.getContentResolver().query(BaseContract.FavoriteCategoriesEntry.URI,
                null,
                null,//all
                null,//all
                BaseContract.FavoriteCategoriesEntry.FAV_NAME);//sort by name

        ArrayList<FavoriteCategory> list = new ArrayList<FavoriteCategory>();

        if (cursor.moveToFirst()) {
            do {
                FavoriteCategory favoriteCategory = new FavoriteCategory();
                favoriteCategory.setId(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry._ID)));//this id
                        // is generated automatically,
                favoriteCategory.setFav_name(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry.FAV_NAME)));
                favoriteCategory.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry.COLOR)));
                favoriteCategory.setIcon(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry.ICON)));
                list.add(favoriteCategory);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public FavoriteCategory getFavoriteCategory(long id){
        String selection = BaseContract.FavoriteCategoriesEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        FavoriteCategory favoriteCategory = new FavoriteCategory();

        Cursor cursor = context.getContentResolver().query(BaseContract.FavoriteCategoriesEntry.URI,
                null,
                selection,//all
                selectionArgs,//all
                null);//this query returns only one

        if(cursor.moveToFirst()){

            //when we load data
            //we need to marks loaded
            favoriteCategory.setId(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry._ID)));//this id is generated automatically,
            favoriteCategory.setFav_name(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry.FAV_NAME)));
            favoriteCategory.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry.COLOR)));
            favoriteCategory.setColor(cursor.getString(cursor.getColumnIndex(BaseContract.FavoriteCategoriesEntry.ICON)));
        }
        return favoriteCategory;
    }
}
