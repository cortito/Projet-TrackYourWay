package fr.trackyourway;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;

public class RunnerActivity extends AppCompatActivity {

    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner);

        Button buttonStartRun = (Button) findViewById(R.id.startRunBtn);
        buttonStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RunnerActivity.this, RunningActivity.class);
                startActivity(intent);
            }
        });


    }
}
