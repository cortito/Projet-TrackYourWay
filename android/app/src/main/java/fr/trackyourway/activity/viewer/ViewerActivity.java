package fr.trackyourway.activity.viewer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import fr.trackyourway.R;
import fr.trackyourway.business.alertdialog.FilterBibDialog;
import fr.trackyourway.business.alertdialog.FilterTeamDialog;
import fr.trackyourway.business.services.RetrieveRunnerTimerTask;
import fr.trackyourway.business.spinner.FilterSpinner;
import fr.trackyourway.model.RunnerWrap;

import static fr.trackyourway.R.id.map;

public class ViewerActivity extends FragmentActivity implements OnMapReadyCallback, FilterBibDialog.FilterBibDialogListener, FilterTeamDialog.FilterTeamDialogListener {
    private static final String TAG = ViewerActivity.class.getSimpleName();
    private static final float ZOOM_INDEX = 16;
    private static final LatLng CENTER = new LatLng(45.7847083, 4.8697467);


    private GoogleMap mMap;
    private RetrieveRunnerTimerTask retrieveRunnerTimerTask;
    private FilterSpinner spinner;
    private Button resetButton;

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
        spinner = (FilterSpinner) findViewById(R.id.spinnerFilter);

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
                    case 0:
                        resetMarkerAndZoom();
                        break;
                    case 1:
                        FilterBibDialog alertDialog = new FilterBibDialog(ViewerActivity.this);
                        alertDialog.show(getFragmentManager(), TAG);
                        break;
                    case 2:
                        FilterTeamDialog teamDialog = new FilterTeamDialog(ViewerActivity.this, retrieveRunnerTimerTask.getTeamMap());
                        teamDialog.show(getFragmentManager(), TAG);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Reset Button
         */
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetMarkerAndZoom();
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CENTER, ZOOM_INDEX), 1500, null);
    }

    private void resetMarkerAndZoom() {
        zoomMap();
        onTeamClick("");
    }

    private void resetMarkers(List<RunnerWrap> runners){
        if(runners == null){
            runners = new ArrayList<>(retrieveRunnerTimerTask.getRunnerWrapMap().values());
        }
        for (RunnerWrap r : runners) {
            r.getMarker().setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
    }

    @Override
    public void onIdBib(int idBib) {
        resetMarkers(null);
        RunnerWrap rw = retrieveRunnerTimerTask.getRunnerWrapMap().get(idBib);

        if (rw != null) {
            Marker m = rw.getMarker();
            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            LatLng marker = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, ZOOM_INDEX + 2), 1500, null);
        } else {
            Toast.makeText(getApplicationContext(), "Can't find runner", Toast.LENGTH_LONG).show();
            resetMarkerAndZoom();
        }
    }

    @Override
    public void onTeamClick(String teamName) {
        List<RunnerWrap> runners = new ArrayList<>(retrieveRunnerTimerTask.getRunnerWrapMap().values());

        if (!teamName.isEmpty()) {
            for (RunnerWrap r : runners)
                // Highlights the runners in the team
                if (r.getRunner().getTeamName().equals(teamName)) {
                    r.getMarker().setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
                // Other teams
                else {
                    r.getMarker().setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
        } else {
            resetMarkers(runners);
        }
    }
}

