package com.bloc.blocnotes.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.bloc.blocnotes.R;

/**
 * Created by Wayne on 11/8/2014.
 */
public class DeleteService extends Service {
    @Override
    public void onCreate() {
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        manager.cancel(getString(R.string.note_due),0);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
