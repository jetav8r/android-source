package com.bloc.android.blocly.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.bloc.android.blocly.BloclyApplication;
import com.bloc.android.blocly.utilities.Message;
import com.bloc.blocly.R;

/**
 * Created by Wayne on 12/23/2014.
 */
public class BloclyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocly);
        Message.message(this, BloclyApplication.getSharedDataSource().getFeeds().get(0).getTitle());
    }
}
