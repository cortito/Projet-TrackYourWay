package fr.trackyourway.activity.viewer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import fr.trackyourway.R;
import fr.trackyourway.business.alertdialog.FilterBibDialog;
import fr.trackyourway.business.services.RetrieveRunnerTimerTask;

import static fr.trackyourway.R.id.map;

public class ViewerActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = ViewerActivity.class.getSimpleName();
    private static final float ZOOM_INDEX = 16;

    private GoogleMap mMap;
    private RetrieveRunnerTimerTask retrieveRunnerTimerTask;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        /**
         * Fill up the spinner
         */
        spinner = (Spinner) findViewById(R.id.spinnerFilter);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filterStringArray, R.layout.spinner_style);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        FilterBibDialog alertDialog = new FilterBibDialog();
                        alertDialog.show(getFragmentManager(), TAG);
                        break;
                    case 2:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
