package fr.trackyourway.business.dao;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.trackyourway.model.RunnerModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by bab on 19/10/16.
 *
 * Call the server to get the Runners List (JSON)
 * Return a List of RunnerModel when it is done
 *
 * Create an interface RunnerListener to share the List of RunnerModel
 */

public class RetrieveRunnersTask extends AsyncTask<Void, Void, List<RunnerModel>> {
    private static final String URL_GET = "http://bab-laboratory.com/TrackYourWay/connectbdd.php";
    private static final String TAG = RetrieveRunnersTask.class.getSimpleName();
    private final RunnerListener runnerListener;
    private OkHttpClient client;


    public RetrieveRunnersTask(RunnerListener callback) {
        this.runnerListener = callback;
    }

    @Override
    protected List<RunnerModel> doInBackground(Void... params) {
        List<RunnerModel> runnersList = new ArrayList<>();
        client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(URL_GET)
                .build();

        try {
            String result = client.newCall(request)
                    .execute()
                    .body()
                    .string();
            RunnerModel[] runnersArray = new Gson().fromJson(result, RunnerModel[].class);
            runnersList = Arrays.asList(runnersArray);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return runnersList;
    }

    @Override
    protected void onPostExecute(List<RunnerModel> s) {
        super.onPostExecute(s);
        super.cancel(true);
        runnerListener.onRunnersRetrieved(s);
    }

    public interface RunnerListener {
        void onRunnersRetrieved(List<RunnerModel> s);
    }
}
