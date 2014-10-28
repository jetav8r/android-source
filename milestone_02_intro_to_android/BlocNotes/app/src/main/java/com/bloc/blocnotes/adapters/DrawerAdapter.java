package com.bloc.blocnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bloc.blocnotes.bd.Notebook;

import java.util.ArrayList;

/**
 * Created by Wayne on 10/25/2014.
 */
public class DrawerAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Notebook> values;
    private LayoutInflater inflater;

    public DrawerAdapter(Context context, ArrayList<Notebook> values){
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(android.R.layout.simple_list_item_activated_1, parent, false);//inflating the view
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);//the text view to value

        textView.setText(values.get(position).getName()); //we getting the name of notebook from arraylist
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
