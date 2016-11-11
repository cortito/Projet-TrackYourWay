package fr.trackyourway.activity.runner.Alert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import fr.trackyourway.R;
import fr.trackyourway.business.alertdialog.AlertDialog;
import fr.trackyourway.business.dao.AsyncSendingAlertTask;

public class AlertActivity extends FragmentActivity implements AsyncSendingAlertTask.SendingAlertListener, AlertDialog.AlertDialogListener {

    private static final String TAG = AlertActivity.class.getSimpleName();
    private static final int REQUEST_CAPTURE = 1313;
    ImageView resphoto;
    private AsyncSendingAlertTask asyncSendingAlertTask;
    GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation = null;
    LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Button alertSendBtn = (Button) findViewById(R.id.sendAlertBtn);

        Bundle extras = getIntent().getExtras();
        double latitude = extras.getDouble("latitude");
        double longitude = extras.getDouble("longitude");

        mLastLocation.setLatitude(latitude);
        mLastLocation.setLongitude(longitude);


        alertSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.setmListener(AlertActivity.this);
                alertDialog.show(getFragmentManager(),TAG);
            }
        });
    }


    @Override
    public void onTakingPhotoAlert() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CAPTURE );
        resphoto = (ImageView) findViewById(R.id.pic);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo =(Bitmap) extras.get("data");
            resphoto.setImageBitmap(photo);
        }
    }

    @Override
    public void onSendAlertMsg() {
        
        asyncSendingAlertTask = new AsyncSendingAlertTask(this);
        asyncSendingAlertTask.execute(mLastLocation);
        Toast.makeText(this, "envoie de l'alerte au serveur", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendingAlert(String o) {

    }
}
