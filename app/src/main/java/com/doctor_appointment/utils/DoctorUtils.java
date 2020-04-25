package com.doctor_appointment.utils;
import android.util.Log;
import android.widget.Toast;
import com.doctor_appointment.DoctorAppointmentApplication;
import java.text.DateFormat;
import java.util.Date;

public class DoctorUtils {
    public static final long ONE_DAY = 1 * 24 * 60 * 60 * 1000;
    public static String getDateString(long dateInMilli) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return format.format(new Date(dateInMilli));
    }

    public static boolean getAppointmentDatevalidity(long dateInMilli) {
        long currentTime = new Date().getTime();
        if (dateInMilli - currentTime >= ONE_DAY) {
            return false;
        } else {
            Toast.makeText(DoctorAppointmentApplication.getContext(), "You mush pick appointment date two days from the present date",
                    Toast.LENGTH_LONG).show();
            return true;
        }
    }
}
