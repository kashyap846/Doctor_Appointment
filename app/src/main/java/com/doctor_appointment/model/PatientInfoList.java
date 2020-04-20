package com.doctor_appointment.model;

import com.doctor_appointment.database.DoctorAppointmentDBHelper;

import java.util.ArrayList;

public class PatientInfoList {
    private static PatientInfoList INSTANCE;
    private ArrayList<PatientInfo> patientInfoList;

    public static void initialize(){
        INSTANCE = new PatientInfoList();
    }
    private PatientInfoList(){
        //get patients list from DB
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
    public void add(String name, int age, String disease, String symptoms ,String phoneNum){
        PatientInfo patientInfo = new PatientInfo(name,age,disease,symptoms,phoneNum);
        patientInfoList.add(0,patientInfo);
        //insert into database
        DoctorAppointmentDBHelper.getInstance().insert(patientInfo);
    }

    public void remove(PatientInfo patientInfo){
        patientInfoList.remove(patientInfo);
        //Delete from database
        DoctorAppointmentDBHelper.getInstance().delete(patientInfo);
    }


}
