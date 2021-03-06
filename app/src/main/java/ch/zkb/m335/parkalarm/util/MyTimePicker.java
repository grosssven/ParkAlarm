/*
 * @author: Sven Gross
 * @date:   16. Juni 2015
 *
 * Diese Klasse
 */
package ch.zkb.m335.parkalarm.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

import ch.zkb.m335.parkalarm.ParkActivity;

public class MyTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private ParkActivity parkActivity = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int hour;
        int minute;

        if(parkActivity == null) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
        else {
            //Set the time from ParkActivity(ArrivalTime)
            hour = Integer.parseInt(parkActivity.getArrivalTime().substring(0, 2));
            minute = Integer.parseInt(parkActivity.getArrivalTime().substring(3, 5));
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(android.widget.TimePicker view, int hour, int minute) {
        String hourString = Integer.toString(hour);
        String minuteString = Integer.toString(minute);

        if(hourString.length() == 1) {
            hourString = "0" + hourString;
        }
        if(minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }

        parkActivity.setArrivalInView(hourString + ":" + minuteString);
    }

    public void setParkActivity(ParkActivity parkActivity) {
        this.parkActivity = parkActivity;
    }
}
