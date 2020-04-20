package com.doctor_appointment.listeners;

import com.doctor_appointment.model.PatientInfo;

public interface OnPatientChangedListener {
    void onPatientRemoved(PatientInfo patientInfo);
}
