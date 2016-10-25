package fr.trackyourway.business.dao;

import android.location.Location;
import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by corto on 21/10/2016.
 */

public class AsyncSendingRunnerTask extends AsyncTask<Location, String, String> {

    private static final String URL_POST = "http://bab-laboratory.com/TrackYourWay/updateLocation.php";
    private final RunnerSendListener runnerSendListener;
    private OkHttpClient client;


    public AsyncSendingRunnerTask(RunnerSendListener callback) {
        this.runnerSendListener = callback;
    }


    @Override
    protected String doInBackground(Location... params) {
        client = new OkHttpClient().newBuilder().build();
        String response = "";
        StringBuilder url = new StringBuilder(URL_POST)
                .append("?idBib=")
                .append(0)
                .append("&latitude=")
                .append(params[0].getLatitude())
                .append("&longitude=")
                .append(params[0].getLongitude());
        try {
            Request request = new Request.Builder()
                    .url(url.toString())
                    .build();
             response = client.newCall(request).execute().body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        runnerSendListener.onRunnerRunning(o);
    }




    public interface RunnerSendListener {
        void onRunnerRunning(String o);
    }
}
