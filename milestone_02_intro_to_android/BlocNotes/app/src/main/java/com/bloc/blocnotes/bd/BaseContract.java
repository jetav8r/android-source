package com.bloc.blocnotes.bd;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Wayne on 10/25/2014.
 */
public class BaseContract {
    private static final Uri AUTHORITY_URI = Uri
            .parse("content://com.bloc.blocnotes.bd.BaseProvider"); //this uri accesses the provider

    public BaseContract() {
    }

    public static abstract class NotebooksEntry implements BaseColumns {
        public static final String TABLE = "notebooks"; //this is the name of table
        public static final String NAME = "name";//this is the first column
        public static final String DESCRIPTION = "description"; //this is the second column
        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE); //this is how the provider identifies our table
    }

    public static abstract class NotesEntry implements BaseColumns{
        public static final String TABLE = "notes"; //this is the name of table
        public static final String BODY = "body";//this is the first column
        public static final String REFERENCE = "reference"; //this is the second column
        public static final String IMAGE_URL  = "image_url";
        public static final Uri URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE); //this is how the provider identifies our table
    }
}
