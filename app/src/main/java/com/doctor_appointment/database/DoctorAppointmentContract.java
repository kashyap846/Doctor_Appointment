package com.doctor_appointment.database;

import android.provider.BaseColumns;

public class DoctorAppointmentContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Appointment.db";

    private DoctorAppointmentContract(){}

    public static class DoctorAppointmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "appointments";


        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_DISEASE = "disease";
        public static final String COLUMN_NAME_SYMPTOMS = "symptoms";
        public static final String COLUMN_NAME_CREATED_TIME = "createdTime";
        public static final String COLUMN_NAME_PHONE_NUM = "phoneNum";

    }
}
