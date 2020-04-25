package com.doctor_appointment.model;

import android.util.Log;

import com.doctor_appointment.database.DoctorAppointmentDBHelper;
import com.doctor_appointment.utils.DoctorUtils;

import java.util.ArrayList;

public class PatientInfoList {
    private static PatientInfoList INSTANCE;
    private ArrayList<PatientInfo> patientInfoList;

    public static void initialize(){
        INSTANCE = new PatientInfoList();
    }
    private PatientInfoList(){
        patientInfoList = DoctorAppointmentDBHelper.getInstance().getPatientInfo();
    }

    public static PatientInfoList getInstance(){
        return INSTANCE;
    }

    public int getCount(){
        return patientInfoList.size();
    }
    public PatientInfo get(int position){
        return patientInfoList.get(position);
    }
    public void add(String name, int age, String disease, String symptoms ,String phoneNum,long appointmentDate){
        PatientInfo patientInfo = new PatientInfo(name,age,disease,symptoms,phoneNum,appointmentDate);
        patientInfoList.add(0,patientInfo);
        DoctorAppointmentDBHelper.getInstance().insert(patientInfo);
    }

    public void remove(PatientInfo patientInfo){
        patientInfoList.remove(patientInfo);
        DoctorAppointmentDBHelper.getInstance().delete(patientInfo);
    }


}
