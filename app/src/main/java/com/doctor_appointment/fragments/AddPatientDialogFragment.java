package com.doctor_appointment.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.doctor_appointment.R;
import com.doctor_appointment.listeners.OnNewPatientListener;
import com.doctor_appointment.model.PatientInfoList;
import com.google.android.material.textfield.TextInputEditText;

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

        Button addPatient = view.findViewById(R.id.add);
        addPatient.setOnClickListener(this::addPatientButtonClick);
        return view;
    }

    private void addPatientButtonClick(View view){
        if(nameEditText.getText()==null || ageEditText.getText()==null || diseaseEditText.getText()==null || symptomsEditText.getText()==null || phoneNumEditText.getText()== null){
            return;
        }
        String name = nameEditText.getText().toString();
        String phoneNum = phoneNumEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String disease = diseaseEditText.getText().toString();
        String symptoms = symptomsEditText.getText().toString();
        if(name.isEmpty() || age.isEmpty() || disease.isEmpty() || symptoms.isEmpty()){
            return;
        }
        Log.e("addPatientButtonClick: ", "PatientInfoList.getInstance()"+PatientInfoList.getInstance());
        PatientInfoList.getInstance().add(name, Integer.parseInt(age),disease,symptoms,phoneNum);
    if(listener!= null){
            listener.onNewPatient();
        }
        dismiss();
    }
}
