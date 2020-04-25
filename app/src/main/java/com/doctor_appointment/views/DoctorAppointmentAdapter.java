package com.doctor_appointment.views;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor_appointment.DoctorAppointmentApplication;
import com.doctor_appointment.R;
import com.doctor_appointment.fragments.AddPatientDialogFragment;
import com.doctor_appointment.fragments.ViewPatientDialogFragment;
import com.doctor_appointment.listeners.OnPatientChangedListener;
import com.doctor_appointment.model.PatientInfo;
import com.doctor_appointment.model.PatientInfoList;
import com.doctor_appointment.utils.DoctorUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.DoctorAppointmentViewHolder>  implements OnPatientChangedListener{
    FragmentManager fragmentManager;
    public DoctorAppointmentAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return PatientInfoList.getInstance().get(position).getId();
    }

    @NonNull
    @Override
    public DoctorAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_appointment,parent,false);
        return new DoctorAppointmentViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentViewHolder holder, int position) {
        holder.populate(PatientInfoList.getInstance().get(position),this);
    }



    @Override
    public int getItemCount() {
        return PatientInfoList.getInstance().getCount();
    }

    @Override
    public void onPatientRemoved(PatientInfo patientInfo) {
        PatientInfoList.getInstance().remove(patientInfo);
        notifyDataSetChanged();
    }


    public class DoctorAppointmentViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        public TextView getName() {
            return name;
        }

        public TextView getAge() {
            return age;
        }

        public TextView getPhoneNum() {
            return phoneNum;
        }

        private TextView name;
        private TextView age;
        private TextView phoneNum;

        public TextView getAppointmentTime() {
            return appointmentTime;
        }

        private TextView appointmentTime;
        private PatientInfo patientInfo;
        private OnPatientChangedListener changedListener;
        public DoctorAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.age = itemView.findViewById(R.id.age);
            this.phoneNum = itemView.findViewById(R.id.phone_num);
            this.name = itemView.findViewById(R.id.name);
            this.appointmentTime = itemView.findViewById(R.id.appointment_date);
        }

        public void populate(PatientInfo patientInfo,OnPatientChangedListener changedListener){
            this.patientInfo = patientInfo;
            this.changedListener = changedListener;
            name.setText(patientInfo.getName());
            age.setText(String.valueOf(patientInfo.getAge()));
            phoneNum.setText(patientInfo.getPhoneNum());
            appointmentTime.setText("Appointment Date "+ DoctorUtils.getDateString(patientInfo.getAppointmentDate()));
            itemView.setOnClickListener(this::onClick);
        }
        private void onClick(View view){
//            new AlertDialog.Builder(view.getContext())
//                    .setTitle("Patient info")
//                    .setMessage("Name::     "+patientInfo.getName()+"\n"+
//                            "Age::        "+patientInfo.getAge()+"\n"+
//                            "Number::       "+patientInfo.getPhoneNum()+"\n"+
//                            "Disease::      "+patientInfo.getDisease()+"\n"+
//                            "Symptoms::     "+patientInfo.getSymptoms()+"\n"+
//                            "Appointment Time::     "+DoctorUtils.getDateString(patientInfo.getAppointmentDate())) //"CreationTime::     "+dateFormat
//                    .setNegativeButton("cancel",(a,b) -> {})
//                    .setPositiveButton("Delete",(a,b) ->{changedListener.onPatientRemoved(patientInfo);})
//                    .show();

//            new ViewPatientDialogFragment().Builder()
//                    .(adapter::notifyDataSetChanged)
//                    .show(getSupportFragmentManager(),"add_new_dialog");
//            new ViewPatientDialogFragment.Builder()
//                    .viewPatientListener(this::deletePatient)
//                    .show(getSupportFragmentManager(),"view_dialog");
            new ViewPatientDialogFragment.Builder()
                   .viewPatientListener(this::deletePatient,patientInfo)
                   .show( fragmentManager,"view_dialog");


        }

        private void deletePatient() {
            changedListener.onPatientRemoved(patientInfo);
        }


    }



}
