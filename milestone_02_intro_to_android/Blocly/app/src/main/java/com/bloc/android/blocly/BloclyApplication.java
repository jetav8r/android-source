package com.bloc.android.blocly;

import android.app.Application;

import com.bloc.android.blocly.api.DataSource;

/**
 * Created by Wayne on 12/24/2014.
 */
public class BloclyApplication extends Application {

    private static BloclyApplication sharedInstance;
    private DataSource dataSource;

    // #1
    public static BloclyApplication getSharedInstance() {
        return sharedInstance;
    }

    // #2
    public static DataSource getSharedDataSource() {
        return BloclyApplication.getSharedInstance().getDataSource();
    }

    // #3
    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
        dataSource = new DataSource();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
