package com.doctor_appointment.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.doctor_appointment.R;
import com.doctor_appointment.database.DoctorAppointmentDBHelper;

public class DoctorAppointmentWidgetProvider extends AppWidgetProvider {
    private static final int APPOINTMENT_VIEW_ID = R.id.appointments;
    private static final int PATIENT_VIEW_ID = R.id.patients;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int appointmentCount = DoctorAppointmentDBHelper.getInstance().getPatientCount();
        int patientsCount = DoctorAppointmentDBHelper.getInstance().getUniquePatient();
        Log.e("onUpdate: ","patientsCount"+patientsCount );
        for(int i=0;i< appWidgetIds.length;i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews remoteViews =  new RemoteViews(context.getPackageName(),R.layout.app_widget_doctor_appointment);
            remoteViews.setViewVisibility(R.id.container, View.VISIBLE);
            populateAppointmentsCount(remoteViews,appointmentCount,APPOINTMENT_VIEW_ID);
            populatePatientsCount(remoteViews,patientsCount,PATIENT_VIEW_ID);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }

    }

    private void populatePatientsCount(RemoteViews remoteViews, int patientsCount, int patientViewId) {
        remoteViews.setTextViewText(patientViewId,"Total Patients till date "+patientsCount);
    }

    private void populateAppointmentsCount(RemoteViews remoteViews, int appointmentCount, int appointmentViewId) {
        Log.e("populateAppointment: ", "rgr"+appointmentCount);
        remoteViews.setTextViewText(appointmentViewId,"Total Appointments till date "+appointmentCount);
    }
}
