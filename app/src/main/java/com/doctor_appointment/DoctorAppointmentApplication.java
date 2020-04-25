package com.doctor_appointment;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.doctor_appointment.database.DoctorAppointmentDBHelper;

public class DoctorAppointmentApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        DoctorAppointmentApplication.context = getApplicationContext();
        Log.e( "onCreate: ", "DoctorAppointmentApplication");
        DoctorAppointmentDBHelper.initialize(getApplicationContext());
    }

    public static Context getContext(){
        return context;
    }
}
