package fr.trackyourway.business.spinner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by bab on 25/10/16.
 */

public class FilterSpinner extends Spinner {
    OnItemSelectedListener listener;

    public FilterSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterSpinner(Context context) {
        super(context);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);

        if (position == getSelectedItemPosition()) {
            listener.onItemSelected(null, null, position, 0);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

}
