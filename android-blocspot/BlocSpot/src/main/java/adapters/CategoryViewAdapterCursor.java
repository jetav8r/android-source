package adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
import com.bloc.blocspot.model.CategoriesDao;
import com.bloc.blocspot.model.Category;
import com.bloc.blocspot.utilities.Message;

/**
 * Created by Wayne on 11/28/2014.
 */
public class CategoryViewAdapterCursor extends SimpleCursorAdapter implements View.OnClickListener {

    LayoutInflater inflater;
    Context context;
    private int layout = R.layout.custom_adapter_categories; //this is the view we use... can create our own view
    protected String mNewCategoryName;
    protected Category category;
    protected CheckBox checked;
    //private ImageLoader imgLoader;

    public CategoryViewAdapterCursor(Context context, int layout, Cursor c, String[] from, int[] to) {//cursor is now our data list
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

        final TextView textView = (TextView) view.findViewById(R.id.textViewCategoryName);
        final String text = cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.FRIENDLY_NAME));
        final String googleName = cursor.getString(cursor.getColumnIndex(BaseContract.CategoriesEntry.GOOGLE_NAME));

        textView.setText(text);
        final int mPosition = cursor.getPosition();

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView_menu);
        imageView.setFocusable(false);
        final PopupMenu popupMenu = new PopupMenu(context, imageView);
        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Delete category");
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Rename category");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Wayne", "selected text = " + googleName);
                //Now I want to call GetPlaces and pass the selectedText to it
                new BlocSpotActivity().new GetPlaces(context, googleName).execute();
            }
        });


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case 0:
                    // so we put delete note here
                    //passing the position
                    deleteCategory(mPosition);
                    break;
                case 1:
                    // we put rename note here
                    renameCategory(mPosition);
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

    protected void deleteCategory(final int mPosition) {
        Category currentCategory = (Category)getItem(mPosition);
        CategoriesDao categoriesDao = new CategoriesDao(context);
        categoriesDao.delete(currentCategory);
    }

    protected void renameCategory(final int mPosition) {
        final EditText userInput = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Category Name");
        builder.setView(userInput);
        builder.setMessage("New Category Name?");
        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Editable newText = userInput.getText();
                mNewCategoryName=newText.toString();
                Category currentCategory = (Category)getItem(mPosition);
                currentCategory.setFriendly_name(mNewCategoryName);
                CategoriesDao categoriesDao = new CategoriesDao(context);;
                categoriesDao.update(currentCategory);//now we update category
                //changeCategory(mPosition, mNewCategoryName);
            }
        });
        builder.show();



    }

    private void changeCategory(final int mPosition, final String mNewCategoryName) {


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
