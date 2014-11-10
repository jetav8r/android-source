package com.bloc.blocnotes.services;

/**
 * Created by Wayne on 11/9/2014.
 */
public final class CommonConstants {
    public CommonConstants() {

        // don't allow the class to be instantiated
    }

    // Milliseconds in the snooze duration, which translates
    // to 20 seconds.
    public static final int SNOOZE_DURATION = 3000;
    public static final int DEFAULT_TIMER_DURATION = 4000;
    public static final String ACTION_SNOOZE = "com.bloc.blocnotes.service.ACTION_SNOOZE";
    public static final String ACTION_DISMISS = "com.bloc.blocnotes.service.ACTION_DISMISS";
    public static final String ACTION_EDIT = "com.bloc.blocnotes.service.ACTION_EDIT";
    public static final String ACTION_DELETE = "com.bloc.blocnotes.service.ACTION_DELETE";
    public static final String SNOOZE_MESSAGE= "Snoozing for ten minutes";
    public static final String EXTRA_MESSAGE= "Time to review note";
    public static final String EXTRA_TIMER = "com.bloc.blocnotes.service.EXTRA_TIMER";
    public static final int NOTIFICATION_ID = 001;
    public static final String DEBUG_TAG = "Wayne";
}
