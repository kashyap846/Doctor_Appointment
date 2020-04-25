package com.doctor_appointment.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.doctor_appointment.DoctorAppointmentApplication;
import com.doctor_appointment.R;
import com.doctor_appointment.listeners.OnNewPatientListener;
import com.doctor_appointment.model.PatientInfoList;
import com.doctor_appointment.utils.DoctorUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AddPatientDialogFragment extends DialogFragment {

    public static class Builder{
        private AddPatientDialogFragment fragment = new AddPatientDialogFragment();

        public Builder addOnNewPatientListener(OnNewPatientListener listener){
            fragment.listener = listener;
            return this;
        }

        public void show(FragmentManager fragmentManager,String tag){
            fragment.show(fragmentManager,tag);
        }
    }
    private AddPatientDialogFragment(){}
    private OnNewPatientListener listener;
    private TextInputEditText nameEditText,ageEditText,diseaseEditText,symptomsEditText,phoneNumEditText;
    private TextView dateTextView;
    private long appointmentDate;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.add_patient_fragment,container,false);
        nameEditText = view.findViewById(R.id.name);
        phoneNumEditText = view.findViewById(R.id.phone_num);
        ageEditText = view.findViewById(R.id.age);
        diseaseEditText = view.findViewById(R.id.disease);
        symptomsEditText = view.findViewById(R.id.symptoms);
        dateTextView = view.findViewById(R.id.appointment_date);
        dateTextView.setOnClickListener(this::addDatePickDialog);
        Button addPatient = view.findViewById(R.id.add);
        addPatient.setOnClickListener(this::addPatientButtonClick);
        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    private void addDatePickDialog(View view) {
        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.setTime(appointmentDate > 0 ? new Date(appointmentDate) : new Date());
        int year = appointmentCalendar.get(Calendar.YEAR);
        int month = appointmentCalendar.get(Calendar.MONTH);
        int day = appointmentCalendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(view.getContext(),this::onAppointmentDateSet,year,month,day).show();
    }

    private void onAppointmentDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month,dayOfMonth);
        appointmentDate = calendar.getTimeInMillis();
        dateTextView.setText(DoctorUtils.getDateString(appointmentDate));

    }
    private void addPatientButtonClick(View view){
        if(nameEditText.getText()==null || ageEditText.getText()==null || diseaseEditText.getText()==null || symptomsEditText.getText()==null || phoneNumEditText.getText()== null)
        {
            return;
        }

        String name = nameEditText.getText().toString();
        String phoneNum = phoneNumEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String disease = diseaseEditText.getText().toString();
        String symptoms = symptomsEditText.getText().toString();
        if(name.isEmpty() || age.isEmpty() || disease.isEmpty() || symptoms.isEmpty()){
            Toast.makeText(DoctorAppointmentApplication.getContext(), "Please fill all the details properly",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(DoctorUtils.getAppointmentDatevalidity(appointmentDate)){
            return;
        }
        PatientInfoList.getInstance().add(name, Integer.parseInt(age),disease,symptoms,phoneNum,appointmentDate);
        if(listener!= null){
            listener.onNewPatient();
        }
        dismiss();
    }
}
