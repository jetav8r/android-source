package com.bloc.android.blocly.api.model;

/**
 * Created by Wayne on 12/24/2014.
 */
public class RssItem {
    private String id;
    private String guid;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String datePublished;
    private String enclosure;
    private String mimeType;
    private int rssFeed;
    private boolean read;
    private boolean favorite;
    private boolean archived;

    public RssItem(String guid, String title, String description, String url, String imageUrl, String datePublished, boolean read, boolean favorite, boolean archived) {
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.datePublished = datePublished;
        this.read = read;
        this.favorite = favorite;
        this.archived = archived;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getRssFeed() {
        return rssFeed;
    }

    public void setRssFeed(int rssFeed) {
        this.rssFeed = rssFeed;
    }

}
