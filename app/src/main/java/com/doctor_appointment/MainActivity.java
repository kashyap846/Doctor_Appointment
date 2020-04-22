package com.doctor_appointment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.doctor_appointment.database.DoctorAppointmentDBHelper;
import com.doctor_appointment.fragments.AddPatientDialogFragment;
import com.doctor_appointment.views.DoctorAppointmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private DoctorAppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAppointmentAdapter();
        recyclerView.setAdapter(adapter);
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(this::addPatient);
    }

    private void addPatient(View view){
        //new AddNewDialogFragment
        new AddPatientDialogFragment.Builder()
                .addOnNewPatientListener(adapter::notifyDataSetChanged)
                .show(getSupportFragmentManager(),"add_new_dialog");
    }

}
