package com.doctor_appointment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class PatientInfo {
    private long id;
    private String name;
    private int age;
    private String disease;
    private String phoneNum;
    private String symptoms;
    private long createdTime;
    private long appointmentDate;

    public PatientInfo(long id, String name, int age, String disease, String symptoms, long createdTime,String phoneNum,long appointmentDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.symptoms = symptoms;
        this.createdTime = createdTime;
        this.phoneNum = phoneNum;
        this.appointmentDate = appointmentDate;
    }

    public PatientInfo(String name, int age, String disease, String symptoms, String phoneNum, long appointmentDate) {
        this(new Random().nextLong(),name,age,disease,symptoms,new Date().getTime(),phoneNum,appointmentDate);
    }

    public long getAppointmentDate() {
        return appointmentDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDisease() {
        return disease;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientInfo that = (PatientInfo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
