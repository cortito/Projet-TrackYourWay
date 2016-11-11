package fr.trackyourway.business.alertdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.Map;

import fr.trackyourway.R;
import fr.trackyourway.model.TeamModel;

/**
 * Created by bab on 25/10/16.
 *
 * Create a list window to select a specific team
 * Display all the team
 */

public class FilterTeamDialog extends DialogFragment {

    private FilterTeamDialogListener mListener;
    private Map<String, TeamModel> teamMap;

    public void setmListener(FilterTeamDialogListener mListener) {
        this.mListener = mListener;
    }

    public void setTeamMap(Map<String, TeamModel> teamMap) {
        this.teamMap = teamMap;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] teams = teamMap.keySet().toArray(new String[0]);
        builder.setTitle(R.string.teamSelect)
                .setItems(teams, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onTeamClick(teams[id]);
                    }
                });
        return builder.create();

    }

    public interface FilterTeamDialogListener {
        void onTeamClick(String teamName);
    }
}
