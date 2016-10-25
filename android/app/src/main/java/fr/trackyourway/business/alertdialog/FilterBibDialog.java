package fr.trackyourway.business.alertdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import fr.trackyourway.R;

/**
 * Created by bab on 25/10/16.
 */

public class FilterBibDialog extends DialogFragment {


    private FilterBibDialogListener mListener;

    public FilterBibDialog(FilterBibDialogListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater;
        inflater = getActivity().getLayoutInflater();
        final View viewInflater;
        viewInflater = inflater.inflate(R.layout.dialog_bib_filter, null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        viewInflater.findViewById(R.id.bib);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewInflater)
                .setMessage("Filtering by bib")
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = (EditText) viewInflater.findViewById(R.id.bib);
                        mListener.onIdBib(Integer.parseInt(et.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface FilterBibDialogListener {
        void onIdBib(int idBib);
    }

}
