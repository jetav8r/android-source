package com.bloc.blocnotes.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.bloc.blocnotes.BlocNotes;
import com.bloc.blocnotes.R;
import com.bloc.blocnotes.model.Note;
import com.bloc.blocnotes.services.CommonConstants;
import com.bloc.blocnotes.services.PingService;

/**
 * Created by Wayne on 11/7/2014.
 */
public class ReminderReceiver extends BroadcastReceiver {
    Context context;
    //private Intent mServiceIntent;
    /*private static int mWhen;

    public static int getWhen() {
        return mWhen;
    }

    public static void setWhen(int when) {
        mWhen = when;
    }
    */
    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        //mServiceIntent = new Intent(context, PingService.class);


        Note note = (Note)intent.getExtras().getSerializable("note");

        Intent editIntent = new Intent(context, BlocNotes.class);
        editIntent.setAction(CommonConstants.ACTION_EDIT);
        editIntent.putExtra("note",note);
        PendingIntent piEdit = PendingIntent.getActivity(context, 0, editIntent, 0);

        Intent deleteIntent = new Intent(context, PingService.class);
        deleteIntent.setAction(CommonConstants.ACTION_DELETE);
        deleteIntent.putExtra("note",note);
        PendingIntent piDelete = PendingIntent.getService(context, 0, deleteIntent, 0);

        Intent snoozeIntent = new Intent(context, PingService.class);
        snoozeIntent.putExtra("note", note);
        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(context, 0, snoozeIntent, 0);

        //Message.message(context, "onReceive started");
        /*if ("SHOW_NOTIFICATION".equals(intent.getAction())) {
            //Message.message(context, "Message received");
            String msgText = "Your note is ready for editing.  "
                    + "You can edit the note, delete it or snooze.  "
                    + "Touch the notification to edit your note.";
            Builder builder = new Notification.Builder(context)
                    .setContentTitle("Note is due")
                    .setContentText("Touch to activate")
                    .setSmallIcon(R.drawable.ic_stat_image_edit)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .addAction(R.drawable.ic_delete, "Delete", piDelete)
                    .addAction(R.drawable.ic_snooze, "Snooze", piSnooze);
            Notification notification = new Notification.BigTextStyle(builder)
                    .bigText(msgText).build();
            notification.flags|=Notification.FLAG_AUTO_CANCEL;
            //notification.flags|=Notification.FLAG_NO_CLEAR;//this makes it so you CAN NOT clear the notification
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
*/

        String msgText = "Your note is ready for editing.  "
                + "You can edit the note, delete it or snooze.  "
                + "Touch the notification to edit your note.";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(context.getString(R.string.note_content_title))
                    .setContentText(context.getString(R.string.note_content_text))
                    .setSmallIcon(R.drawable.ic_stat_image_edit)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(piEdit)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_delete, context.getString(R.string.delete_text), piDelete)
                    .addAction(R.drawable.ic_snooze, context.getString(R.string.snooze_text), piSnooze)
                     //.setStyle(new NotificationCompat.InboxStyle().setSummaryText(msgText));
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(note.getBody()));
        //Notification notification = builder.build();
            /*Notification notification =
                    new Notification.Builder(context)
                            .setSmallIcon(R.drawable.ic_stat_image_edit)
                            .setContentTitle("Note is due")
                            .setContentText("onReceive is working")
                            .setContentIntent(pIntent)
                            .setAutoCancel(true)
                            .addAction(R.drawable.ic_delete, "Delete", piDelete)
                            .addAction(R.drawable.ic_snooze, "More", piSnooze)
                            .build();
            */
        //int when = (int) System.currentTimeMillis();
        //setWhen(when);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }
}
