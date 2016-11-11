package fr.trackyourway.activity.runner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import fr.trackyourway.R;
import fr.trackyourway.activity.runner.Alert.AlertActivity;
import fr.trackyourway.business.dao.AsyncSendingRunnerTask;
import fr.trackyourway.business.services.SendingRunnerTimerTask;
import okhttp3.OkHttpClient;

public class RunningActivity extends AppCompatActivity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AsyncSendingRunnerTask.RunnerSendListener {


    public final static int UPDATE_RATE = 5000;
    public final static int MY_PERMISSION_REQUEST_READ_FINE_LOCATION = 111;
    GoogleApiClient mGoogleApiClient = null;


    private Location mCurrentLocation = null;

    //private AsyncSendingRunnerTask asyncSendingRunnerTask;
    private SendingRunnerTimerTask sendingRunnerTimerTask;
    LocationRequest locationRequest;

    //String mLastUpdateTime = null;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        Button stopBtn = (Button) findViewById(R.id.stopRunBtn);
        Button alertBtn = (Button) findViewById(R.id.alertBtn);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.disconnect();
                Log.d("tag", "going to stop");
                if (!mGoogleApiClient.isConnected()) {
                    Intent intent = new Intent(RunningActivity.this, RunnerActivity.class);
                    startActivity(intent);
                }
            }
        });

        alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.disconnect();
                Log.d("tag", "going to Alert");
                if (!mGoogleApiClient.isConnected()) {
                    Intent intent = new Intent(RunningActivity.this, AlertActivity.class);
                    startActivity(intent);
                }
            }
        });

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        locationRequest = new LocationRequest();
        createLocationRequest(locationRequest);


    }

    protected void createLocationRequest(LocationRequest mLocationRequest) {
        mLocationRequest.setInterval(UPDATE_RATE);
        mLocationRequest.setFastestInterval(UPDATE_RATE);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Toast.makeText(RunningActivity.this,
                "attempt connection, :)",
                Toast.LENGTH_LONG).show();



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(RunningActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},
                    MY_PERMISSION_REQUEST_READ_FINE_LOCATION);

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);


    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RunningActivity.this,
                            "permission was granted, :)",
                            Toast.LENGTH_LONG).show();

                    try{
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                mGoogleApiClient, locationRequest, this);
                    }catch(SecurityException e){
                        Toast.makeText(RunningActivity.this,
                                "SecurityException:\n" + e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RunningActivity.this,
                            "permission denied, ...:(",
                            Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(sendingRunnerTimerTask != null){
            sendingRunnerTimerTask.onPause();
        }
        
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        sendingRunnerTimerTask = new SendingRunnerTimerTask(mCurrentLocation);
        sendingRunnerTimerTask.start();

        Toast.makeText(this, "Location sent...", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onRunnerRunning(String o) {

    }
}
