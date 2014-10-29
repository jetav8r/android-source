package com.bloc.blocnotes.model;

import java.io.Serializable;

/**
 * Created by Wayne on 10/25/2014.
 */
public class Notebook implements Serializable {
    private long id;

    private String name;
    private String description;

    public Notebook(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
