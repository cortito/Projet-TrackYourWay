package fr.trackyourway.business.alertdialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import fr.trackyourway.R;

/**
 * Created by corto_000 on 26/10/2016.
 */

public class AlertDialog extends DialogFragment {

    private AlertDialogListener mListener;
    private TextView dialogText;
    private CountDownTimer timer;

    public AlertDialog() {    }


    public void setmListener(AlertDialogListener mListener) {
        this.mListener = mListener;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final LayoutInflater inflater;
        inflater = getActivity().getLayoutInflater();

        final View viewInflater;
        viewInflater = inflater.inflate(R.layout.dialog_alert, null);
        dialogText = (TextView) viewInflater.findViewById(R.id.dialogTxt);

        // Use the Builder class for convenient dialog construction
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setView(viewInflater)
                .setTitle(getString(R.string.alertTitle))
                .setPositiveButton(getString(R.string.TakepicBtn), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timer.cancel();
                        mListener.onSendAlertMsg();
                        mListener.onTakingPhotoAlert();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        timer.cancel();
                    }
                });

        timer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                dialogText.setText((millisUntilFinished / 1000) + " " + getString(R.string.countdown));
            }

            public void onFinish() {
                Log.d("tag", "on envoie la donnée");
                if (getDialog() != null) {
                    getDialog().cancel();
                }
                mListener.onSendAlertMsg();

            }


        }.start();

        return builder.create();
    }


    public interface AlertDialogListener {
        void onTakingPhotoAlert();
        void onSendAlertMsg();
    }
}
