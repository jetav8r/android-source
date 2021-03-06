package com.bloc.blocnotes.model;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Wayne on 10/25/2014.
 */
public class Note implements Serializable {

    private long id;
    private String imageUrl;
    private String body;
    private String reference;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
