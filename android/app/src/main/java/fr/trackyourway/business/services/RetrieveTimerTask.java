package fr.trackyourway.business.services;

import android.util.Log;

import java.util.TimerTask;

/**
 * Created by bab on 21/10/16.
 */

public class RetrieveTimerTask extends TimerTask {
    public static final String TAG = RetrieveTimerTask.class.getSimpleName();

    @Override
    public void run() {
        Log.d(TAG,"run");
    }
}
