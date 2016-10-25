package fr.trackyourway.business.services;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import fr.trackyourway.business.dao.AsyncSendingRunnerTask;

/**
 * Created by corto_000 on 25/10/2016.
 */

public class SendingRunnerTimerTask extends TimerTask implements AsyncSendingRunnerTask.RunnerSendListener {

    private static final int PERIOD = 5000; //ms
    private final Timer timer;
    private Location location;

    private AsyncSendingRunnerTask asyncSendingRunnerTask;

    public SendingRunnerTimerTask(Location mLocation) {
        timer = new Timer();
        location = mLocation;
    }

    @Override
    public void run() {

        asyncSendingRunnerTask = new AsyncSendingRunnerTask(this);
        if(asyncSendingRunnerTask.getStatus() == AsyncTask.Status.PENDING) {
            try {
                sendRunner();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

     private void sendRunner() throws IOException {
        if (asyncSendingRunnerTask != null) {
            asyncSendingRunnerTask.execute(location);
            Log.d("Runner", "Position envoyé");
        }
    }


    public void start() {
        timer.scheduleAtFixedRate(this, 0, PERIOD); // 0 = start now
    }

    public void onPause() {
        if (asyncSendingRunnerTask != null) {
            asyncSendingRunnerTask.cancel(true);
            timer.cancel();
        }
    }

    @Override
    public void onRunnerRunning(String o) {

    }
}
