package com.doctor_appointment;

import android.app.Application;
import android.util.Log;

import com.doctor_appointment.database.DoctorAppointmentDBHelper;

public class DoctorAppointmentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e( "onCreate: ", "DoctorAppointmentApplication");
        DoctorAppointmentDBHelper.initialize(getApplicationContext());
    }
}
