package fr.trackyourway.viewer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import fr.trackyourway.R;
import fr.trackyourway.dao.RetrieveRunnersTask;
import fr.trackyourway.model.RunnerModel;

import static fr.trackyourway.R.id.map;

public class ViewerActivity extends FragmentActivity implements OnMapReadyCallback, RetrieveRunnersTask.RunnerListener {
    public static final int delay = 1000; // ms
    public static final Handler h = new Handler();
    private static final String TAG = ViewerActivity.class.getSimpleName();
    private static final float ZOOM_INDEX = 16;

    private GoogleMap mMap;
    private RetrieveRunnersTask retrieveRunnersTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        retrieveRunnersTask = new RetrieveRunnersTask(this);
        /**
         * Retrieve all runners
         */
        if (retrieveRunnersTask.getStatus() == AsyncTask.Status.PENDING) {
            try {
                runRetrieveRunner();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "update");
        }

        h.postDelayed(new Runnable(){
            public void run(){
                //do something
                h.postDelayed(this, delay);
            }
        }, delay);

            zoomMap();
    }

    void runRetrieveRunner() throws IOException {
        if (retrieveRunnersTask != null) {
            retrieveRunnersTask.execute();
            
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (retrieveRunnersTask != null) {
            retrieveRunnersTask.cancel(true);
        }

    }

    @Override
    public void onRunnersRetrieved(List<RunnerModel> res) {
        Log.d(TAG, res.size() + "");
        // runners = new ArrayList<>(res);
        LatLng markerRunner = null;
        for (RunnerModel r : res) {
            r.getInfo();
            markerRunner = new LatLng(r.getLatitude(), r.getLongitude());
            mMap.addMarker(new MarkerOptions().position(markerRunner).title(r.getInfo()));
        }
    }

    private void zoomMap() {
        LatLng center = new LatLng(45.7847083, 4.8697467);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, ZOOM_INDEX));

    }
}
