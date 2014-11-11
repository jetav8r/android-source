package com.bloc.blocnotes.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bloc.blocnotes.BlocNotes;
import com.bloc.blocnotes.R;
import com.bloc.blocnotes.model.Note;
import com.bloc.blocnotes.model.NotesDao;
import com.bloc.blocnotes.util.ReminderReceiver;

/**
 * Created by Wayne on 11/8/2014.
 */
public class PingService extends IntentService {
    Context context;
    //private String mMessage;

    public PingService() {
        super("com.bloc.blocnotes.util.ReminderReceiver");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Note note = (Note) intent.getExtras().getSerializable(this.getString(R.string.note_parameter_notification));
        //int mWhen = ReminderReceiver.getWhen();
        Log.e("Note on HandleIntent", note.getBody());
        String action = intent.getAction();
        // The reminder message the user set.
        //mMessage = intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
        // The timer duration the user set. The default is 10 seconds.
        //mMillis = intent.getIntExtra(CommonConstants.EXTRA_TIMER,
                //CommonConstants.DEFAULT_TIMER_DURATION);
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        //NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        //manager.cancel(mWhen);

        if (action.equals(CommonConstants.ACTION_SNOOZE)) {
            manager.cancel(CommonConstants.NOTIFICATION_ID);
            snoozeAction(note);
            //mMessage = intent.getStringExtra(CommonConstants.SNOOZE_MESSAGE);
            //Toast.makeText(this, mMessage, Toast.LENGTH_LONG).show();
        } else {
            if (action.equals(CommonConstants.ACTION_DELETE)) {
                manager.cancel(CommonConstants.NOTIFICATION_ID);
                deleteAction(note);
            } /*else {
                if (action.equals(CommonConstants.ACTION_DISMISS)) {
                    manager.cancel(CommonConstants.NOTIFICATION_ID);
                    manager.cancelAll();
                }
            }*/
        }
    }

    private void deleteAction(Note note) {
        NotesDao notesDao = new NotesDao(this);
        notesDao.delete(note);
        note = null;
    }


    private void snoozeAction(Note note) {
        Intent reminderReceiverIntent = new Intent(this, ReminderReceiver.class);
        reminderReceiverIntent.setAction("SHOW_NOTIFICATION");
        //reminderReceiverIntent.putExtra("EXTRA_REMINDER_TITLE", "Note is due for editing");
        reminderReceiverIntent.putExtra("note", note);
        //String action =  reminderReceiverIntent.getAction();
        // Make a Broadcasting PendingIntent based on the previous
        final PendingIntent reminderPendingIntent = PendingIntent.getBroadcast(this, 0, reminderReceiverIntent, 0);
        final AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + CommonConstants.SNOOZE_DURATION, reminderPendingIntent);//for testing I will change to 3 seconds
        //Toast.makeText(this, "You will be reminded again in 10 minutes", Toast.LENGTH_SHORT).show();

        //Message.message(context, "You will be reminded again in 10 minutes");

    }

    /* void issueNotification(Note note, String msg) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Sets up the Snooze and Dismiss action buttons that will appear in the
        // expanded view of the notification.
        Intent dismissIntent = new Intent(this, PingService.class);
        dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

        Intent snoozeIntent = new Intent(this, PingService.class);
        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);
        // Constructs the Builder object.
        builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_image_edit)
                        .setContentTitle(getString(R.string.note_content_title))
                        .setContentText(getString(R.string.note_content_text))
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                /*
                 * Sets the big view "big text" style and supplies the
                 * text (the user's reminder message) that will be displayed
                 * in the detail area of the expanded notification.
                 * These calls are ignored by the support library for
                 * pre-4.1 devices.
                 */
                       /* .setStyle(new NotificationCompat.BigTextStyle(note.getBody()).bigText(msg))
                        .addAction (R.drawable.ic__notification_dismiss, getString(R.string.dismiss), piDismiss)
                        .addAction(R.drawable.ic_snooze, getString(R.string.snooze_text), piSnooze);
        /*
         * Clicking the notification itself displays ResultActivity, which provides
         * UI for snoozing or dismissing the notification.
         * This is available through either the normal view or big view.
         */
        /*Intent resultIntent = new Intent(this, BlocNotes.class);
        resultIntent.putExtra((note.getBody()), msg);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        startTimer(mMillis);
    }

    private void issueNotification(NotificationCompat.Builder builder) {
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Including the notification ID allows you to update the notification later on.
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }

    // Starts the timer according to the number of seconds the user specified.
    private void startTimer(int millis) {
        Log.d(CommonConstants.DEBUG_TAG, getString(R.string.timer_start));
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Log.d(CommonConstants.DEBUG_TAG, getString(R.string.sleep_error));
        }
        Log.d(CommonConstants.DEBUG_TAG, getString(R.string.timer_finished));
        issueNotification(builder);
    }
    */
}
