package fr.trackyourway.activity.viewer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import fr.trackyourway.R;
import fr.trackyourway.business.services.RetrieveRunnerTimerTask;

import static fr.trackyourway.R.id.map;

public class ViewerActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = ViewerActivity.class.getSimpleName();
    private static final float ZOOM_INDEX = 16;

    private GoogleMap mMap;
    private RetrieveRunnerTimerTask retrieveRunnerTimerTask;

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
        retrieveRunnerTimerTask = new RetrieveRunnerTimerTask(mMap);
        retrieveRunnerTimerTask.start();

        zoomMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        retrieveRunnerTimerTask.onPause();
    }

    private void zoomMap() {
        LatLng center = new LatLng(45.7847083, 4.8697467);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, ZOOM_INDEX));

    }
}
