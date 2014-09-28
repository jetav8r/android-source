package com.bloc.blocnotes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wayne on 9/27/2014.
 */
public class BlocNotesHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "blocnotes";
    private static final String TABLE1_NAME = "notebooks";
    private static final int DATABASE_VERSION = 1;
    private static final String UID = "_id";
    private static final String nb_id = "nb_identifier";
    private static final String name = "name ";
    private static final String description = "description";
    private static final String DROP_TABLE= "DROP TABLE IF EXISTS" +TABLE1_NAME+"";

    private static final String CREATE_NOTEBOOKS =
            "CREATE TABLE "+TABLE1_NAME+
                    "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +nb_id+" VARCHAR(255),"
                    +name+" VARCHAR(255)," +description+" VARCHAR(255)" +" )";

    public BlocNotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        Message.message(context,"constructor was called");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_NOTEBOOKS);
            Message.message(context,"onCreate was called");
        } catch (SQLException e) {
            Message.message(context,""+e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            Message.message(context,"onUpgrade was called");
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            Message.message(context,""+e);
        }

    }
}
