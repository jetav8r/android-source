package com.bloc.android.blocly.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Wayne on 12/24/2014.
 */
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
