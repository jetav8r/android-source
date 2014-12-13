package com.bloc.blocspot.model;

/**
 * Created by Wayne on 11/28/2014.
 */
public class Category {
    private String id;
    private String friendly_name;
    private String google_name;
    private String favorite;
    private String color;
    private int marker_icon;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFriendly_name() {
        return friendly_name;
    }

    public void setFriendly_name(String friendly_name) {
        this.friendly_name = friendly_name;
    }

    public String getGoogle_name() {
        return google_name;
    }

    public void setGoogle_name(String google_name) {
        this.google_name = google_name;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMarker_icon() {
        return marker_icon;
    }

    public void setMarker_icon(int marker_icon) {
        this.marker_icon = marker_icon;
    }
}
