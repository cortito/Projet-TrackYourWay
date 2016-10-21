package fr.trackyourway.business.dao;

import android.os.AsyncTask;

/**
 * Created by corto on 21/10/2016.
 */

public class AsyncSendingRunnerTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    public interface RunnerSendListener {
        void  onRunnerRunning();
    }
}
