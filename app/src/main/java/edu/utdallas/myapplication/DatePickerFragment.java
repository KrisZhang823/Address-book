package edu.utdallas.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Allow user to select date using android provided controls
 * Full tutorial on: http://developer.android.com/guide/topics/ui/controls/pickers.html
 * Created by Peiyang on 3/14/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private int year;
    private int month;
    private int day;
    private boolean defaultSet = false;

    // The fragment that implements the interface
    private Fragment callBackFragment;

    public interface NoticeDialogListener {
        public void onDateSet(int year, int month, int day);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) callBackFragment;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(callBackFragment.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(!defaultSet) {
            // Use the current date as the default date in the picker if not assigned
            final Calendar c = Calendar.getInstance();
            this.year = c.get(Calendar.YEAR);
            this.month = c.get(Calendar.MONTH);
            this.day = c.get(Calendar.DAY_OF_MONTH);
        }

        // Create a new instance of DatePickerDialog and return it
        // year, month and day indicate the default date that shows on creating
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        defaultSet = true;
        mListener.onDateSet(year, month, day);
    }

    // Set default date for show
    public void setDefaultDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        defaultSet = true;
    }

    // Set the fragment class that implements the callbacks
    public void setCallBackFragment(Fragment callBackFragment) {
        this.callBackFragment = callBackFragment;
    }
}

