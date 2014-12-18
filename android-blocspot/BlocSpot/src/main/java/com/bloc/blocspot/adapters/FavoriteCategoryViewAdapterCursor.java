package com.bloc.blocspot.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Editable;
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

import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.database.BaseContract;
import com.bloc.blocspot.model.CategoriesDao;
import com.bloc.blocspot.model.Category;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;
import com.bloc.blocspot.utilities.Message;

import java.util.ArrayList;

/**
 * Created by Wayne on 12/11/2014.
 */
public class FavoriteCategoryViewAdapterCursor extends SimpleCursorAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    Context context;
    private int layout = R.layout.custom_adapter_fav_categories; //this is the view we use... can create our own view
    protected String mNewCategoryName;
    //protected Category category;
    protected CheckBox checked;
    //private ImageLoader imgLoader;

    public FavoriteCategoryViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
        super(context, layout, c, from, to, 0);
        this.inflater = LayoutInflater.from(context);//to inflate a view
        this.context = context;
    }

    @Override
    public Object getItem(int position) {//this method returns a notebook
        getCursor().moveToPosition(position);//this cursor contains our data, moving this to selected position by user
        //then gets data from this position
        Category category = new Category();
        category.setId(getCursor().getString(getCursor().getColumnIndex(BaseContract.CategoriesEntry._ID)));//this id is generated automatically,
        category.setFriendly_name(getCursor().getString(getCursor().getColumnIndex(BaseContract.CategoriesEntry.FRIENDLY_NAME)));
        category.setGoogle_name(getCursor().getString(getCursor().getColumnIndex(BaseContract.CategoriesEntry.GOOGLE_NAME)));
        category.setColor(getCursor().getString(getCursor().getColumnIndex(BaseContract.CategoriesEntry.COLOR)));
        category.setMarker_icon(getCursor().getInt(getCursor().getColumnIndex(BaseContract.CategoriesEntry.MARKER_ICON)));
        return category;// returns it
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

        TextView textView = (TextView) view.findViewById(R.id.textViewFavCategoryName);
        String text = cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FRIENDLY_NAME));
        String catColor = cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.COLOR));
        if (catColor==null) { catColor="#40c4ff";}
        textView.setBackgroundColor(Color.parseColor(catColor));
        textView.setText(text);

        final int mPosition = cursor.getPosition();

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_fav_cat_menu);
        imageView.setFocusable(false);
        final PopupMenu popupMenu = new PopupMenu(context, imageView);
        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Assign color to favorite");
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Remove category from favorites");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "Rename favorite category");
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, "Add favorite category");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        colorCategory(mPosition);
                        break;
                   case 1:
                        removeFavCategory(mPosition);
                        break;
                    case 2:
                        // we put rename note here
                        renameFavCategory(mPosition);
                        break;
                    case 3:
                        createFavCategory(mPosition);
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
    }

    private void removeFavCategory(int mPosition) {
        final CategoriesDao categoriesDao = new CategoriesDao(context);
        final Category category = (Category) getItem(mPosition);
        String currentCategory = category.getGoogle_name();
        if (currentCategory.equals("default_category")) {
            Message.message(context, "You can not delete this category");
            return;
        }
        category.setFavorite("");
        categoriesDao.update(category);

    }

    private void colorCategory(final int mPosition) {
        final CategoriesDao categoriesDao = new CategoriesDao(context);
        final Category category = (Category) getItem(mPosition);
        final String[] colorList = context.getResources().getStringArray(R.array.color_list);
        final String[] material_colors = context.getResources().getStringArray(R.array.material_color);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose category color");
        builder.setItems(colorList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        String currentCatColor = material_colors[0];
                        int icon = R.drawable.ic_favorite_red;
                        category.setColor(currentCatColor);
                        category.setMarker_icon(icon);
                        category.setFavorite("Y");
                        categoriesDao.update(category);
                        setFavPlacesCatColor(category.getGoogle_name(),currentCatColor);
                        break;
                    case 1:
                        currentCatColor = material_colors[1];
                        icon = R.drawable.ic_favorite_purple;
                        category.setColor(currentCatColor);
                        category.setMarker_icon(icon);
                        category.setFavorite("Y");
                        categoriesDao.update(category);
                        setFavPlacesCatColor(category.getGoogle_name(),currentCatColor);
                        break;
                    case 2:
                        currentCatColor = material_colors[2];
                        icon = R.drawable.ic_favorite_blue;
                        category.setColor(currentCatColor);
                        category.setMarker_icon(icon);
                        category.setFavorite("Y");
                        categoriesDao.update(category);
                        setFavPlacesCatColor(category.getGoogle_name(),currentCatColor);
                        break;
                    case 3:
                        currentCatColor = material_colors[3];
                        icon = R.drawable.ic_favorite_green;
                        category.setColor(currentCatColor);
                        category.setMarker_icon(icon);
                        category.setFavorite("Y");
                        //setFavCatColor(currentCatColor, icon);
                        categoriesDao.update(category);
                        setFavPlacesCatColor(category.getGoogle_name(),currentCatColor);
                        break;
                    case 4:
                        currentCatColor = material_colors[4];
                        icon = R.drawable.ic_favorite_yellow;
                        category.setColor(currentCatColor);
                        category.setMarker_icon(icon);
                        category.setFavorite("Y");
                        categoriesDao.update(category);
                        setFavPlacesCatColor(category.getGoogle_name(),currentCatColor);
                        break;
                    case 5:
                        currentCatColor = material_colors[5];
                        icon = R.drawable.ic_favorite_orange;
                        category.setColor(currentCatColor);
                        category.setMarker_icon(icon);
                        category.setFavorite("Y");
                        categoriesDao.update(category);
                        setFavPlacesCatColor(category.getGoogle_name(),currentCatColor);
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void setFavPlacesCatColor(String currentCatName, String currentCatColor) {
        PlacesDao placesDao = new PlacesDao(context);
        Place place = new Place();

        ArrayList<Place> currentFilteredPlaces = placesDao.getPlacesInCategory(currentCatName);
        for (int i = 0; i < currentFilteredPlaces.size(); i++) {
            Place currentPlace = currentFilteredPlaces.get(i);
            currentPlace.setColor(currentCatColor);
            placesDao.update(currentPlace);
            //Message.message(context, "method working");
        }


    }

    /*
    private void setFavCatColor(String currentCatColor, int icon) {
        PlacesDao placesDao = new PlacesDao(context);
        Place place = new Place();
        place.setColor(currentCatColor);
        place.setMarkerIcon(icon);
        //put code to set colored icons here
        placesDao.update(place);
    }
    */

    private void createFavCategory(final int mPosition) {
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
                Category currentCategory = (Category) getItem(mPosition);
                currentCategory.setFriendly_name(mNewCategoryName);
                currentCategory.setFavorite("Y");
                currentCategory.setGoogle_name(mNewCategoryName);
                currentCategory.setColor("#40c4ff");
                //currentCategory.setIcon();
                CategoriesDao categoriesDao = new CategoriesDao(context);
                try {
                    categoriesDao.insert(currentCategory);//now we update category
                    Message.message(context, "Favorite added");
                } catch (Exception e) {
                    Message.message(context, "Favorite already added");
                }

                //changeCategory(mPosition, mNewCategoryName);
            }
        });
        builder.show();
    }

    protected void deleteCategory(final int mPosition) {
        Category currentCategory = (Category) getItem(mPosition);
        CategoriesDao categoriesDao = new CategoriesDao(context);
        categoriesDao.delete(currentCategory);
    }

    protected void renameFavCategory(final int mPosition) {
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
                Category currentCategory = (Category) getItem(mPosition);
                currentCategory.setFriendly_name(mNewCategoryName);
                currentCategory.setFavorite("Y");
                CategoriesDao categoriesDao = new CategoriesDao(context);
                try {
                    categoriesDao.update(currentCategory);//now we update category
                    Message.message(context, "Favorite added");
                } catch (Exception e) {
                    Message.message(context, "Favorite already added");
                }

                //changeCategory(mPosition, mNewCategoryName);
            }
        });
        builder.show();
    }


    @Override
    public void onClick(View view) {
    }
}
