package com.doctor_appointment.views;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.doctor_appointment.R;
import com.doctor_appointment.listeners.OnPatientChangedListener;
import com.doctor_appointment.model.PatientInfo;
import com.doctor_appointment.model.PatientInfoList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.DoctorAppointmentViewHolder>  implements OnPatientChangedListener{
    //private ArrayList<PatientInfo> data;

    public DoctorAppointmentAdapter() {
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
        Log.e("onBindViewHolder: ", ""+PatientInfoList.getInstance().get(position).getAge());
        holder.populate(PatientInfoList.getInstance().get(position),this);
    }



    @Override
    public int getItemCount() {

        Log.e("getItemCount: ", ""+PatientInfoList.getInstance().getCount());
        return PatientInfoList.getInstance().getCount();
    }

    @Override
    public void onPatientRemoved(PatientInfo patientInfo) {
        PatientInfoList.getInstance().remove(patientInfo);
        notifyDataSetChanged();
    }


    public class DoctorAppointmentViewHolder extends RecyclerView.ViewHolder {
        public TextView getName() {
            return name;
        }

        public TextView getAge() {
            return age;
        }

        public TextView getPhoneNum() {
            return phoneNum;
        }

        private TextView name,age,phoneNum;
        private PatientInfo patientInfo;
        private OnPatientChangedListener changedListener;
        public DoctorAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.age = itemView.findViewById(R.id.age);
            this.phoneNum = itemView.findViewById(R.id.phone_num);
            this.name = itemView.findViewById(R.id.name);
        }

        public void populate(PatientInfo patientInfo,OnPatientChangedListener changedListener){
            this.patientInfo = patientInfo;
            this.changedListener = changedListener;
            name.setText(patientInfo.getName());
            age.setText(String.valueOf(patientInfo.getAge()));
            phoneNum.setText(patientInfo.getPhoneNum());
            itemView.setOnClickListener(this::onClick);
        }
        private void onClick(View view){
            Date date = new Date(patientInfo.getCreatedTime());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            format.setTimeZone(TimeZone.getTimeZone("EST"));
            String dateFormat = format.format(date).toString().substring(0,20).replace("T"," ");
            Log.e("onClick: ", "onClick"+patientInfo.getCreatedTime());
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Patient info")
                    .setMessage("Name::     "+patientInfo.getName()+"\n"+
                            "Age::        "+patientInfo.getAge()+"\n"+
                            "Number::       "+patientInfo.getPhoneNum()+"\n"+
                            "Disease::      "+patientInfo.getDisease()+"\n"+
                            "Symptoms::     "+patientInfo.getSymptoms()+"\n"+
                            "CreationTime::     "+dateFormat)
                    .setNegativeButton("cancel",(a,b) -> {})
                    .setPositiveButton("Delete",(a,b) ->{changedListener.onPatientRemoved(patientInfo);})
                    .show();

        }


    }



}
