package fr.trackyourway.activity.runner;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import fr.trackyourway.R;
import fr.trackyourway.business.dao.AsyncSendingRunnerTask;
import fr.trackyourway.business.services.SendingRunnerTimerTask;
import okhttp3.OkHttpClient;

public class RunningActivity extends AppCompatActivity implements ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AsyncSendingRunnerTask.RunnerSendListener {


    GoogleApiClient mGoogleApiClient = null;

    private Location mLastLocation = null;

    //private AsyncSendingRunnerTask asyncSendingRunnerTask;
    private SendingRunnerTimerTask sendingRunnerTimerTask;

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
                Log.d("tag","going to alert");
                if (!mGoogleApiClient.isConnected()) {
                    Intent intent = new Intent(RunningActivity.this, RunnerActivity.class);
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


    }

    /*protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }*/

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

        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            Log.d("TAG", "Connected");

        } catch (SecurityException e) {
            Log.e("PERMISSION EXCEPTION", "PERMISSON_NOT_GRANTED");
        }

        sendingRunnerTimerTask = new SendingRunnerTimerTask(mLastLocation);
        sendingRunnerTimerTask.start();

      // TODO HERE

        //asyncSendingRunnerTask = new AsyncSendingRunnerTask(this);
        /*try {
            sendRunner();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    /*void sendRunner() throws IOException {
        if (asyncSendingRunnerTask != null) {
            asyncSendingRunnerTask.execute(mLastLocation);

        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        sendingRunnerTimerTask.onPause();
        //asyncSendingRunnerTask.cancel(true);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

       /* mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();*/

    }

    private void updateUI() {
       /* mLatitudeText.setText(String.valueOf(mCurrentLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mCurrentLocation.getLongitude()));
        //mLastUpdateTimeTextView.setText(mLastUpdateTime);*/
    }



    @Override
    public void onRunnerRunning(String o) {

    }
}
