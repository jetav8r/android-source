package com.bloc.blocnotes.model;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Wayne on 10/25/2014.
 */
public class Note implements Serializable {

    private long id;

    private String body;
    private String reference;

    private Note instance;
    private boolean loaded;// = false

    private Context context;

    public Note(Context context) {
        this.context = context;
    }

    private void load(){
        if(loaded){//if loaded nothing to do
            return;//then leave method
        }
        //else
        NotesDao notebooksDao = new NotesDao(context);


        instance = notebooksDao.getNote(getId());//we need create method first

        //now we have an object with all data the instance
        this.setBody(instance.getBody());//this call get name too
        this.setReference(instance.getReference());

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

    public String getBody() {
        if(!loaded){
            load();
        }
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReference() {
        if(!loaded){
            load();
        }
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
