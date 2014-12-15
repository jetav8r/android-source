package com.bloc.blocspot.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.database.BaseContract;
import com.bloc.blocspot.fragments.FavCategoryDialogFragment;
import com.bloc.blocspot.model.FavoriteCategoriesDao;
import com.bloc.blocspot.model.FavoriteCategory;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;
import com.bloc.blocspot.utilities.Message;

import java.util.ArrayList;

/**
 * Created by Wayne on 11/28/2014.
 */
public class FavoritePlacesViewAdapterCursor extends SimpleCursorAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    Context context;
    private int layout = R.layout.custom_adapter_favorite_places ; //this is the view we use... can create our own view
    protected String mNewCategoryName;
    protected String mDescription;
    protected Place place;
    protected CheckBox checked;
    protected double destLat;
    protected double destLong;
    private LocationManager locationManager;
    //private ImageLoader imgLoader;

    public FavoritePlacesViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
        super(context, layout, c, from, to, 0);
        this.inflater = LayoutInflater.from(context);//to inflate a view
        this.context = context;
    }

    @Override
    public Object getItem(int position) {//this method returns a place
        getCursor().moveToPosition(position);//this cursor contains our data, moving this to selected position by user
        //then gets data from this position
        Place place = new Place();
        place.setId(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry._ID)));//this id is generated automatically,
        place.setGoogleId(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.GOOGLEID)));
        place.setFav_Category(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.FAV_CATEGORY)));
        place.setFavorite(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.FAVORITE)));
        place.setColor(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.COLOR)));
        place.setName(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.NAME)));
        place.setIcon(getCursor().getString(getCursor().getColumnIndex(BaseContract.PlacesEntry.ICON)));
        place.setVisited(getCursor().getInt(getCursor().getColumnIndex(BaseContract.PlacesEntry.VISITED)));
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
        String catColor = cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.COLOR));
        if (catColor==null) { catColor="#40c4ff";}

        int checkBoxState = cursor.getInt(cursor.getColumnIndex(BaseContract.PlacesEntry.VISITED));
        checked=(CheckBox)view.findViewById(R.id.visitedCheckBox);
        checked.setBackgroundColor((Color.parseColor(catColor)));
        if(checkBoxState == 1){
            checked.setChecked(true);
        }
        TextView textView = (TextView) view.findViewById(R.id.textView_fav_place_name);
        final String text = cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.NAME));
        textView.setText(text);

        //TextView textView2 = (TextView) view.findViewById(R.id.textView_fav_category);
        //final String text2 = cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.FAV_CATEGORY));
        //textView2.setText(text2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView_fav_notes);
        final String text3 = cursor.getString(cursor.getColumnIndex(BaseContract.PlacesEntry.DESCRIPTION));
        if (text3 == null) {
            textView3.setText("Add a note about this place!");
        } else {
            textView3.setText(text3);
        }

        final int mPosition = cursor.getPosition();
        final Place currentPlace = (Place)getItem(mPosition);
        destLat = currentPlace.getLatitude();
        destLong = currentPlace.getLongitude();

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_PlacesMenu);
        imageView.setFocusable(false);
        final PopupMenu popupMenu = new PopupMenu(context, imageView);
        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Delete favorite");
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Change category");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "Add note");
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, "Navigate to");
        popupMenu.getMenu().add(Menu.NONE, 4, Menu.NONE, "Share");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("Wayne", "selected text = " + text);
                String distance = ((BlocSpotActivity) context).CalculationByDistance(destLat, destLong);

                Log.e("Wayne", "distance to place = "+distance);
                //((BlocSpotActivity) context).currentLocation(googleName);
                ((BlocSpotActivity) context).moveCameraToMarker(currentPlace);
            }
        });


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        //delete place
                        deleteFavorite(mPosition);
                        break;
                    case 1:
                        //change category
                        changeCategory(mPosition);
                        break;
                    case 2:
                        //add new category
                        updateNote(mPosition);
                        break;
                    case 3:
                        //navigate to place
                        ((BlocSpotActivity) context).navigateFromListView(destLat, destLong);
                        break;
                    case 4:
                        //share place
                        String name = currentPlace.getName();
                        ((BlocSpotActivity) context).share(name, destLat, destLong);
                        break;
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

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()) {
                    //set 1
                    Place currentPlace = (Place) getItem(mPosition);
                    currentPlace.setVisited(1);
                    PlacesDao placesDao = new PlacesDao(context);
                    placesDao.update(currentPlace);

                }else{
                    //set 0
                    Place currentPlace = (Place) getItem(mPosition);
                    currentPlace.setVisited(0);
                    PlacesDao placesDao = new PlacesDao(context);
                    placesDao.update(currentPlace);
                }
            }
        });

    }

    private void deleteCategory(int mPosition) {
        final PlacesDao placesDao = new PlacesDao(context);
        final Place place = (Place) getItem(mPosition);
        //Message.message(context, "Place category is :" +place.getFav_Category());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final FavoriteCategoriesDao favoriteCategoriesDao = new FavoriteCategoriesDao(context);
        final FavoriteCategory favoriteCategory = new FavoriteCategory();
        ArrayList<FavoriteCategory> favoriteCategoryArrayList = favoriteCategoriesDao.getAllFavoriteCategories();

        final String[] mFavoriteCategories = new String[favoriteCategoryArrayList.size()];
        if(!favoriteCategoryArrayList.isEmpty()) {
            for (int i = 0; i < favoriteCategoryArrayList.size(); i++) {
                mFavoriteCategories[i] = favoriteCategoryArrayList.get(i).getFav_name();
            }
            builder.setTitle("Choose a Category to Delete")
                    .setItems(mFavoriteCategories, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // GET ITEM REFERENCED
                            String currentCategory = mFavoriteCategories[i];
                            place.setFav_Category(currentCategory);
                            //should assign color to default
                            place.setColor("#40c4ff");
                            placesDao.update(place);

                            favoriteCategoriesDao.delete(favoriteCategory);


                        }
                    });
            builder.create();
            builder.show();
        }
    }

    private void updateNote(final int mPosition) {
        final EditText userInput = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Description");
        builder.setView(userInput);
        builder.setMessage("Notes about place");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Editable newText = userInput.getText();
                mDescription = newText.toString();
                Place currentPlace = (Place) getItem(mPosition);
                currentPlace.setDescription(mDescription);
                //would like to be able to pick a color here
                //currentPlace.setColor("#40c4ff");
                PlacesDao placesDao = new PlacesDao(context);
                placesDao.update(currentPlace);//now we update category
                //changeCategory(mPosition, mNewCategoryName);
            }
        });
        builder.show();
    }

    protected void deleteFavorite(final int mPosition) {
        Place currentPlace = (Place) getItem(mPosition);
        PlacesDao placesDao = new PlacesDao(context);
        placesDao.delete(currentPlace);
    }

    protected void newCategory(final int mPosition) {
        final EditText userInput = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Category Name");
        builder.setView(userInput);
        builder.setMessage("New Category Name?");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Editable newText = userInput.getText();
                mNewCategoryName = newText.toString();
                FavoriteCategory favoriteCategory = new FavoriteCategory();
                try {
                    favoriteCategory.setFav_name(mNewCategoryName);
                    FavoriteCategoriesDao favoriteCategoriesDao = new FavoriteCategoriesDao(context);
                    favoriteCategoriesDao.insert(favoriteCategory);
                    //now we also update the currentPlace Fav_Category
                    Place currentPlace = (Place) getItem(mPosition);
                    currentPlace.setFav_Category(mNewCategoryName);
                    currentPlace.setColor("#40c4ff");
                    PlacesDao placesDao = new PlacesDao(context);
                    placesDao.update(currentPlace);//now we update category
                } catch (Exception e) {
                    Message.message(context, "Category already exists");
                }
                //changeCategory(mPosition, mNewCategoryName);
            }
        });
        builder.show();
    }

    private void changeCategory(final int mPosition) {
        final Place place = (Place) getItem(mPosition);
        //now we call dialog, pass place
        FavCategoryDialogFragment favCategoryDialogFragment = new FavCategoryDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("keyPlace", place);
        favCategoryDialogFragment.setArguments(args);
        favCategoryDialogFragment.show(((BlocSpotActivity)context).getFragmentManager(),"");
    }

    /*
    private void updatePlace(int mPosition){
            Place currentPlace = (Place)getItem(mPosition);
            (context).updateNote(currentPlace);
        }
     */

    @Override
    public long getItemId(int position) {//returns id in the position selected
        return super.getItemId(position);
    }

    @Override
    public void onClick(View view) {
    }
}



