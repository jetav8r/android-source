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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }
}

