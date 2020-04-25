package com.doctor_appointment.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.doctor_appointment.R;
import com.doctor_appointment.listeners.OnDeletePatientListener;
import com.doctor_appointment.model.PatientInfo;
import com.doctor_appointment.utils.DoctorUtils;


public class ViewPatientDialogFragment extends DialogFragment {

    public static class Builder{
        private ViewPatientDialogFragment fragment = new ViewPatientDialogFragment();

        public Builder viewPatientListener(OnDeletePatientListener listener, PatientInfo patientInfo){
            fragment.listener = listener;
            fragment.patientInfo = patientInfo;
            return this;
        }

        public void show(FragmentManager fragmentManager,String tag){
            fragment.show(fragmentManager,tag);
        }
    }
    public ViewPatientDialogFragment(){}
    private OnDeletePatientListener listener;
    private PatientInfo patientInfo;
    private TextView nameText,ageText,diseaseText,symptomsText,phoneNumText;
    private TextView dateTextView;
    private long appointmentDate;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.view_patient_fragment,container,false);
        nameText = view.findViewById(R.id.name);
        phoneNumText = view.findViewById(R.id.phone_num);
        ageText = view.findViewById(R.id.age);
        diseaseText = view.findViewById(R.id.disease);
        symptomsText = view.findViewById(R.id.symptoms);
        dateTextView = view.findViewById(R.id.appointment_date);
        Button cancelButton = view.findViewById(R.id.cancel);
        Button deleteButton = view.findViewById(R.id.delete);
        cancelButton.setOnClickListener(this::cancelPatientButtonClick);
        deleteButton.setOnClickListener(this::deletePatientButtonClick);
        displayPatientDetails();
        return view;
    }

    private void displayPatientDetails() {
        nameText.setText("Name: \t"+patientInfo.getName());
        phoneNumText.setText("Phone Number: \t"+patientInfo.getPhoneNum());
        ageText.setText("Age: \t"+patientInfo.getAge());
        diseaseText.setText("Disease: \t"+patientInfo.getDisease());
        symptomsText.setText("Symptoms: \t"+patientInfo.getSymptoms());
        dateTextView.setText("Appointment Date \t"+DoctorUtils.getDateString(patientInfo.getAppointmentDate()));
    }

    private void cancelPatientButtonClick(View view){
        dismiss();

    }
    private void deletePatientButtonClick(View view){
        if(listener!= null){
            listener.onDelete();
        }
        dismiss();
    }
}
