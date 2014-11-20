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
        Log.e("Note on HandleIntent", note.getBody());
        String action = intent.getAction();
        // The reminder message the user set.
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        if (action.equals(CommonConstants.ACTION_SNOOZE)) {
            manager.cancel(CommonConstants.NOTIFICATION_ID);
            snoozeAction(note);

        } else {
            if (action.equals(CommonConstants.ACTION_DELETE)) {
                manager.cancel(CommonConstants.NOTIFICATION_ID);
                deleteAction(note);
            }
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
        reminderReceiverIntent.putExtra("note", note);
        // Make a Broadcasting PendingIntent based on the previous
        final PendingIntent reminderPendingIntent = PendingIntent.getBroadcast(this, 0, reminderReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + CommonConstants.SNOOZE_DURATION, reminderPendingIntent);
    }
}
