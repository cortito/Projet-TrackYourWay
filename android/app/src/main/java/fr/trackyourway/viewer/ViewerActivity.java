package fr.trackyourway.viewer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.trackyourway.R;
import fr.trackyourway.model.RunnerObject;

import static fr.trackyourway.R.id.map;

public class ViewerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float ZOOM_INDEX = 16;

    RunnerObject runner = new RunnerObject(0,45.784747,4.8697113,"Team Dev");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }


    /**
     *
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker and move the camera
        LatLng markerRunner = new LatLng(runner.getLatitude(),runner.getLongitude());
        mMap.addMarker(new MarkerOptions().position(markerRunner).title(runner.getInfo()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerRunner,ZOOM_INDEX));

    }
}
