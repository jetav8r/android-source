package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.bloc.blocspot.model.Place;

/**
 * Created by Wayne on 11/23/2014.
 */
public abstract class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Place> values;

    private LayoutInflater inflater;

    public ListViewAdapter(Context context, ArrayList<Place> values){
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(android.R.layout.simple_list_item_activated_1, parent, false);//inflating the view
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);//the text view to value
        //textView.setText(values.get(position).getBody()); // get the contents of places from arraylist
        textView.setText(values.get(position).getName());
        return rowView;
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

