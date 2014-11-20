package com.bloc.blocnotes.services;

/**
 * Created by Wayne on 11/9/2014.
 */
public final class CommonConstants {
    public CommonConstants() {
        // don't allow the class to be instantiated
    }

    // Milliseconds in the snooze duration, which translates
    // to 10 minutes.
    public static final int SNOOZE_DURATION = 600000;
    public static final String ACTION_SNOOZE = "com.bloc.blocnotes.service.ACTION_SNOOZE";
    public static final String ACTION_DISMISS = "com.bloc.blocnotes.service.ACTION_DISMISS";
    public static final String ACTION_EDIT = "com.bloc.blocnotes.service.ACTION_EDIT";
    public static final String ACTION_DELETE = "com.bloc.blocnotes.service.ACTION_DELETE";
    public static final int NOTIFICATION_ID = 001;
    public static final String DEBUG_TAG = "Wayne";
}
