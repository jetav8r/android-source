package com.bloc.blocnotes.model;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Wayne on 10/25/2014.
 */
public class Notebook implements Serializable {
    private long id;

    private String mName;
    private String mDescription;

    private Context context;

    private Notebook instance;
    private boolean loaded;// = false

    public Notebook(Context context){
        //we need a context to access database
        this.context = context;
    }

    private void load(){
        if(loaded){//if loaded nothing to do
            return;//then leave method
        }
        //else
        NotebooksDao notebooksDao = new NotebooksDao(context);


        instance = notebooksDao.getNotebook(getId());//we need create method first

        //now we have an object with all data the instance
        this.setName(instance.getName());//this call get name too
        this.setDescription(instance.getDescription());

                //Log.e("name", getName()); the error is here, it calls getname inside getname, recursivelly
        //now we remove instance from memory
        //we do not need it anymore
        //because this object has all values passed to local variable
        instance = null;

        //we need to mark loaded as true, if not this loads every time
        loaded = true;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        //when we need to call get name first we check if is loaded
        //if not, we load this
        if(!loaded){//not load
            load();
        }
        //then return
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        //the same thing for all get methods
        if(!loaded){
            load();
        }
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
