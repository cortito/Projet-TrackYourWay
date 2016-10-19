package fr.trackyourway.dao;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.trackyourway.model.RunnerObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by bab on 19/10/16.
 */

public class RetrieveRunnersTask extends AsyncTask<String, String, List<RunnerObject>> {
    public static final String URL_GET = "http://bab-laboratory.com/TrackYourWay/connectbdd.php";
    private static final String TAG = RetrieveRunnersTask.class.getSimpleName();
    private final RunnerListener runnerListener;
    private OkHttpClient client;


    public RetrieveRunnersTask(RunnerListener callback) {
        this.runnerListener = callback;
    }

    @Override
    protected List<RunnerObject> doInBackground(String... params) {
        List<RunnerObject> runnersList = new ArrayList<>();

        if (this.getStatus() == Status.PENDING) {
            client = new OkHttpClient().newBuilder().build();

            Request request = new Request.Builder()
                    .url(URL_GET)
                    .build();

            try {
                String result = client.newCall(request)
                        .execute()
                        .body()
                        .string();
                RunnerObject[] runnersArray = new Gson().fromJson(result, RunnerObject[].class);
                runnersList = Arrays.asList(runnersArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return runnersList;
    }

    @Override
    protected void onPostExecute(List<RunnerObject> s) {
        super.onPostExecute(s);
        runnerListener.onRunnersRetrieved(s);
    }

    public interface RunnerListener {
        void onRunnersRetrieved(List<RunnerObject> s);
    }
}
