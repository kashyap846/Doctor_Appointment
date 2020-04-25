package com.doctor_appointment.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.doctor_appointment.MainActivity;
import com.doctor_appointment.R;
import com.doctor_appointment.database.DoctorAppointmentDBHelper;
import com.doctor_appointment.model.PatientInfo;
import com.doctor_appointment.utils.DoctorUtils;

public class DoctorAppointmentWidgetProvider extends AppWidgetProvider {
    private static final int APPOINTMENT_VIEW_ID = R.id.appointments;
    private static final int PATIENT_VIEW_ID = R.id.patients;
    private static final int OVERDUE_VIEW_ID = R.id.overdue;
    private static final int NEXT_PATIENT_ID = R.id.next_patient;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        int appointmentCount = DoctorAppointmentDBHelper.getInstance().getPatientCount();
        int patientsCount = DoctorAppointmentDBHelper.getInstance().getUniquePatient();
        int overDueCount =  DoctorAppointmentDBHelper.getInstance().getOverDueCount();
        PatientInfo nextPatientInfo = DoctorAppointmentDBHelper.getInstance().getNextPatientInfo();
        for(int i=0;i< appWidgetIds.length;i++){
            int appWidgetId = appWidgetIds[i];
            RemoteViews remoteViews =  new RemoteViews(context.getPackageName(),R.layout.app_widget_doctor_appointment);
            remoteViews.setViewVisibility(R.id.container, View.VISIBLE);
            populateAppointmentsCount(remoteViews,appointmentCount,APPOINTMENT_VIEW_ID);
            populatePatientsCount(remoteViews,patientsCount,PATIENT_VIEW_ID);
            populateOverdueCount(remoteViews,overDueCount,OVERDUE_VIEW_ID);
            populateNextPatientInfo(remoteViews,nextPatientInfo,NEXT_PATIENT_ID);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            remoteViews.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }

    }

    private void populateNextPatientInfo(RemoteViews remoteViews, PatientInfo nextPatientInfo, int nextPatientId) {
        if(nextPatientInfo != null) {
            remoteViews.setTextViewText(nextPatientId, "Next patient is " + nextPatientInfo.getName() + "   " + DoctorUtils.getDateString(nextPatientInfo.getAppointmentDate()) + "\n");
        }else{
            remoteViews.setTextViewText(nextPatientId,"No Patients to show here");
        }
        }

    private void populatePatientsCount(RemoteViews remoteViews, int patientsCount, int patientViewId) {
        remoteViews.setTextViewText(patientViewId,"Total Patients till date "+patientsCount+"\n");
    }

    private void populateAppointmentsCount(RemoteViews remoteViews, int appointmentCount, int appointmentViewId) {
        remoteViews.setTextViewText(appointmentViewId,"Total Appointments till date "+appointmentCount+"\n");
    }

    private void populateOverdueCount(RemoteViews remoteViews, int overdueCount, int overdueViewId) {
        remoteViews.setTextViewText(overdueViewId,"Total Overdue appointments "+overdueCount+"\n");
    }
}
