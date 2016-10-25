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

/**
 * Created by bab on 21/10/16.
 */

public class RetrieveRunnerTimerTask extends TimerTask implements RetrieveRunnersTask.RunnerListener {
    private static final String TAG = RetrieveRunnerTimerTask.class.getSimpleName();
    private static final int PERIOD = 5000; //ms

    private final GoogleMap mMap;
    private final Timer timer;
    private final Map<Integer, Marker> markersMap = new TreeMap<>();
    private RetrieveRunnersTask retrieveRunnersTask;

    public RetrieveRunnerTimerTask(GoogleMap mMap) {
        this.mMap = mMap;
        timer = new Timer();
    }

    public Map<Integer, Marker> getMarkersMap() {
        return markersMap;
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

    public void start() {
        timer.scheduleAtFixedRate(this, 0, PERIOD); // 0 = start now
    }

    public void onPause() {
        if (retrieveRunnersTask != null) {
            retrieveRunnersTask.cancel(true);
            timer.cancel();
        }
    }

    @Override
    public void onRunnersRetrieved(List<RunnerModel> res) {
        for (RunnerModel r : res) {
            if (!markersMap.containsKey(r.getIdBib())) {
                // MarkerOptions is immutable
                MarkerOptions o = new MarkerOptions()
                        .position(new LatLng(r.getLatitude(), r.getLongitude()))
                        .title(r.getInfo())
                        .flat(true)
                        .draggable(false);
                // Marker is NOT immutable
                Marker m = mMap.addMarker(o);
                // Save the new Marker into the TreeMap
                markersMap.put(r.getIdBib(), m);
            } else {
                // Update the Marker
                markersMap.get(r.getIdBib()).
                        setPosition(new LatLng(r.getLatitude(), r.getLongitude()));
            }
        }
    }
}
