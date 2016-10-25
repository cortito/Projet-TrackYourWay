package fr.trackyourway.model;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by bab on 25/10/16.
 */

public class RunnerWrap {

    private final RunnerModel runner;
    private final Marker marker;

    public RunnerWrap(Marker marker, RunnerModel runner) {
        this.marker = marker;
        this.runner = runner;
    }

    public RunnerModel getRunner() {
        return runner;
    }

    public Marker getMarker() {
        return marker;
    }
}
