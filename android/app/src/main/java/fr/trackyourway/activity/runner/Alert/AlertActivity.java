package fr.trackyourway.activity.runner.Alert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import fr.trackyourway.R;
import fr.trackyourway.business.alertdialog.AlertDialog;
import fr.trackyourway.business.dao.AsyncSendingAlertTask;

public class AlertActivity extends FragmentActivity implements AlertDialog.AlertDialogListener {

    private static final String TAG = AlertActivity.class.getSimpleName();
    private static final int REQUEST_CAPTURE = 1313;
    ImageView resphoto;
    private AsyncSendingAlertTask asyncSendingAlertTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Button alertSendBtn = (Button) findViewById(R.id.sendAlertBtn);


        alertSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog(AlertActivity.this);
                alertDialog.show(getFragmentManager(),TAG);

            }
        });
    }


    @Override
    public void onTakingPhotoAlert(View viewInflater) {
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

    }
}
