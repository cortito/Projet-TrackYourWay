package fr.trackyourway.business.dao;

import android.location.Location;
import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by corto_000 on 27/10/2016.
 */

public class AsyncSendingAlertTask extends AsyncTask< Location, String, String> {

    private static final String URL_POST = "http://bab-laboratory.com/TrackYourWay/updateLocation.php";

    private final SendingAlertListener sendingAlertListener;
    private OkHttpClient client;


    public AsyncSendingAlertTask(SendingAlertListener callback) {
        this.sendingAlertListener = callback;
    }


    @Override
    protected String doInBackground( Location... params) {
        client = new OkHttpClient().newBuilder().build();
        String response = "";
        String typeA="INJURED";
        StringBuilder url = new StringBuilder(URL_POST)
                .append("?alertType=")
                .append(typeA)
                .append("&idBib=")
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
        sendingAlertListener.onSendingAlert(o);
    }




    public interface SendingAlertListener {
        void onSendingAlert(String o);
    }
}
