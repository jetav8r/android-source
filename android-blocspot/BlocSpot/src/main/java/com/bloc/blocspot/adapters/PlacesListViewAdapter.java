package com.bloc.blocspot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bloc.blocspot.BlocSpotActivity;
import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;
import com.bloc.blocspot.utilities.Message;

import java.util.ArrayList;

/**
 * Created by Wayne on 12/2/2014.
 */
public class PlacesListViewAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Place> values;
    private LayoutInflater inflater;
    protected double destLat;
    protected double destLong;

    public PlacesListViewAdapter(Context context, ArrayList<Place> values){
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        destLat = values.get(position).getLatitude();
        destLong = values.get(position).getLongitude();
        View rowView = inflater.inflate(R.layout.custom_adapter_places, parent, false);//inflating our view
        TextView textView = (TextView) rowView.findViewById(R.id.textViewPlaceName);//calling the fields fom our view
        textView.setText(values.get(position).getName()); //we get the contents of Place from arraylist
        TextView textView2 = (TextView) rowView.findViewById(R.id.textView_Vicinity);
        textView2.setText(values.get(position).getVicinity());
        TextView textView1 = (TextView) rowView.findViewById(R.id.textView_mileage);
        textView1.setText(((BlocSpotActivity) context).CalculationByDistance(destLat, destLong));
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView_PlacesMenu);
        imageView.setFocusable(false);

        final PopupMenu popupMenu = new PopupMenu(context, imageView);
        popupMenu.getMenu().add(Menu.NONE, 0, Menu.NONE, "Save to Favorites");
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Navigate to");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "Share");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        //open dialog to select category or add to new category
                        addToFavorite(values.get(position));
                        break;
                    case 1:
                        //make intent to call navigation
                        ((BlocSpotActivity) context).navigateFromListView(destLat, destLong);
                        break;
                    case 2:
                        //make intent to call share
                        String name = values.get(position).getName();
                        ((BlocSpotActivity) context).share(name, destLat, destLong);
                        break;
                }
                return false;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
        return rowView;
    }

    private void addToFavorite(Place place) {
        PlacesDao placesDao = new PlacesDao(context);
        place.setFavorite("Y");
        place.setFav_Category("default_category");
        place.setColor("#40c4ff");
        place.setVisited(0);
        try {
            placesDao.insert(place);
            Message.message(context, "Favorite added");
        } catch (Exception e) {
            Message.message(context, "Favorite already added");
        }
    }

    @Override
    public int getCount() {//count is list size
        return values.size();
    }

    @Override
    public Object getItem(int position) {//item is the item in the position
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {//id is the position in list
        return position;
    }
}
