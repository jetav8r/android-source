package com.bloc.blocspot.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.database.BaseContract;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.utilities.Message;

/**
 * Created by Wayne on 11/23/2014.
 */
public class ListViewAdapterCursor extends SimpleCursorAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    Context context;
    private int layout = R.layout.custom_adapter_places; //this is the view we use... can create our own view
    //private ImageLoader imgLoader;

    public ListViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
        super(context, layout, c, from, to, 0);
        this.inflater = LayoutInflater.from(context);//to inflate a view
        this.context = context;
    }

    @Override
    public Object getItem(int position) {//this method returns a place object
        getCursor().moveToPosition(position);//this cursor contains our data, moving this to selected position by user
                                             //then gets data from this position
        Place place = new Place();
        place.setId(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry._ID)));//this id is generated automatically,
        place.setName(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.NAME)));
        place.setIcon(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.ICON)));
        place.setDescription(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.DESCRIPTION)));
        place.setLatitude(getCursor().getDouble(getCursor().getColumnIndex(BaseContract.PlacesEntry.LATITUDE)));
        place.setLongitude(getCursor().getDouble(getCursor().getColumnIndex(BaseContract.PlacesEntry.LONGITUDE)));
        place.setVicinity(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.VICINITY)));

        return place;// returns it
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {//this creates a view to show to user
        View view = inflater.inflate(layout, null);
        return view;//simple create and return view
    }
    //now we bind data to view

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {//this view is returned for method newview
        super.bindView(view, context, cursor);
        //at this point cursor is in the position
        //then we can get the position here
        final int mPosition = cursor.getPosition();
        final TextView textView = (TextView)view.findViewById(R.id.textViewPlaceName);
        final String text = cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.NAME));
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.message(context, "Item clicked was "+ mPosition);

            }
        });


        ImageView imageView = (ImageView)view.findViewById(R.id.imageView_menu);
        imageView.setFocusable(false);
        final PopupMenu popupMenu = new PopupMenu(context, imageView);
        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Delete place");
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Update description");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "Change category");
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, "Navigate to");
        //popupMenu.getMenu().add(Menu.NONE, 4, Menu.NONE, "Add image for note");

        /*ImageView imageViewUrl = (ImageView)view.findViewById(R.id.imageViewUrl);
        imageViewUrl.setFocusable(false);
        //passing url to imageview
        String newUrl = cursor.getString(cursor.getColumnIndex(BaseContract.NotesEntry.IMAGE_URL));
        imageViewUrl.setTag(newUrl);
        //This is where we call the com.bloc.blocspot.utilities for images from cache
        imgLoader = new ImageLoader(context);
        imgLoader.DisplayImage(newUrl,imageViewUrl);
        */


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        // so we put delete note here
                        //passing the position
                        //deletePlace(mPosition);
                        break;
                    case 1:
                        // we put rename note here
                       // updateNote(mPosition);
                        break;
                    case 2:
                        // we put move note here
                        //changeCategory(mPosition);
                        break;
                    /*case 3:
                        // remind me call here
                        remindMe(mPosition);
                        break;
                    case 4:
                        // image URL call here
                        addUrl(mPosition);
                        break;
                        */
               }
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
    }

/*
    private void changeCategory(final int mPosition) {
        final CategoriesDao categoriesDao = new CategoriesDao(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose New Category")
                .setItems(categoriesDao.getArrayStringCategories(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String [] currentReference = categoriesDao.getArrayStringCategories();// GET ITEM REFERENCED
                        String currentNotebook = currentReference[i];
                        Place place = (Place)getItem(mPosition);//this note
                        //we need only change reference of note which we get from currentReference array
                        place.setReference(currentPlace);
                        PlacesDao placesDao = new PlacesDao(context);
                        PlacesDao.update(place);
                    }
                });
        builder.create();
        builder.show();
    }

    private void updatePlace(int mPosition){
        Place currentPlace = (Place)getItem(mPosition);
        (context).updateNote(currentPlace);
    }

    private void deletePlace(int mPosition){
        //put code here
        //the adapter has a method getitem(position) that returns a Note
        //then get it, cast and delete
        Place currentPlace = (Place)getItem(mPosition);
        PlacesDao placesDao = new PlacesDao(context);
        placesDao.delete(currentPlace);
    }

    @Override
    public long getItemId(int position) {//returns id in the position selected
        return super.getItemId(position);
    }
*/
    @Override
    public void onClick(View view) {
        Message.message(context, "Menu item clicked");
    }
}