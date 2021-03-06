package fr.trackyourway.business.services;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import fr.trackyourway.business.dao.RetrieveRunnersTask;
import fr.trackyourway.model.RunnerModel;
import fr.trackyourway.model.RunnerWrap;
import fr.trackyourway.model.TeamModel;

/**
 * Created by bab on 21/10/16.
 *
 * Send every Xsec a request to the server
 * Get a List of RunnerModel with RetrieveRunnersTask
 * Update the markers of each runner through RunnerWrap
 */

public class RetrieveRunnerTimerTask extends TimerTask implements RetrieveRunnersTask.RunnerListener {
    private static final String TAG = RetrieveRunnerTimerTask.class.getSimpleName();
    private static final int PERIOD = 5000; //ms

    private final GoogleMap mMap;
    private final Timer timer;
    private final Map<Integer, RunnerWrap> runnerWrapMap = new TreeMap<>();
    private final Map<String, TeamModel> teamMap = new TreeMap<>();
    private RetrieveRunnersTask retrieveRunnersTask;

    public RetrieveRunnerTimerTask(GoogleMap mMap) {
        this.mMap = mMap;
        timer = new Timer();
    }

    public Map<Integer, RunnerWrap> getRunnerWrapMap() {
        return runnerWrapMap;
    }

    public Map<String, TeamModel> getTeamMap() {
        return teamMap;
    }

    @Override
    public void run() {

        retrieveRunnersTask = new RetrieveRunnersTask(this);

        /**
         * Retrieve all runners
         */
        if (retrieveRunnersTask.getStatus() == AsyncTask.Status.PENDING) {
            if (retrieveRunnersTask != null) {
                retrieveRunnersTask.execute();
            }
        }
    }

    /**
     * Start the Timer
     */
    public void start() {
        timer.scheduleAtFixedRate(this, 0, PERIOD); // 0 = start now
    }

    public void onPause() {
        if (retrieveRunnersTask != null) {
            retrieveRunnersTask.cancel(true);
            timer.cancel();
        }
    }

    /**
     * Listener from RetrieveRunnersTask
     * @param res
     */
    @Override
    public void onRunnersRetrieved(List<RunnerModel> res) {
        for (RunnerModel r : res) {
            // New runner
            if (!runnerWrapMap.containsKey(r.getIdBib())) {
                /**
                 *  Marker
                 */
                // MarkerOptions is immutable
                MarkerOptions o = new MarkerOptions()
                        .position(new LatLng(r.getLatitude(), r.getLongitude()))
                        .title(r.getInfo())
                        .flat(false)
                        .draggable(false);
                // Marker is NOT immutable
                Marker m = mMap.addMarker(o);
                runnerWrapMap.put(r.getIdBib(), new RunnerWrap(m, r));


                /**
                 * Team
                 */
                // if team exists
                if (!r.getTeamName().isEmpty() && r.getTeamName() != null) {
                    // if not register yet, add new team
                    if (!teamMap.containsKey(r.getTeamName())) {
                        teamMap.put(r.getTeamName(), new TeamModel(r.getTeamName()));
                    }
                    // Add runner to the team
                    teamMap.get(r.getTeamName()).addRunner(r);
                }
            } else {
                runnerWrapMap.get(r.getIdBib()).getMarker()
                        .setPosition(new LatLng(r.getLatitude(), r.getLongitude()));
            }
        }
    }
}
