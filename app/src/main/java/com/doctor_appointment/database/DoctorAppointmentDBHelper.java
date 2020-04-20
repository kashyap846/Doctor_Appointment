package com.doctor_appointment.database;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.doctor_appointment.model.PatientInfo;
import com.doctor_appointment.model.PatientInfoList;
import com.doctor_appointment.widgets.DoctorAppointmentWidgetProvider;

import java.util.ArrayList;

public class DoctorAppointmentDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static DoctorAppointmentDBHelper INSTANCE;
    private static final String SQL_CREATE = "CREATE TABLE " + DoctorAppointmentContract.DoctorAppointmentEntry.TABLE_NAME+ " ("+
            DoctorAppointmentContract.DoctorAppointmentEntry._ID+ " INTEGER PRIMARY KEY,"+
            DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_NAME+ " TEXT,"+
            DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_AGE+ " INTEGER,"+
            DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_DISEASE+ " TEXT,"+
            DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_SYMPTOMS+ " TEXT,"+
            DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_CREATED_TIME+ " INTEGER," +
            DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_PHONE_NUM+ " TEXT" + ")";


    public static DoctorAppointmentDBHelper getInstance(){
        return INSTANCE;
    }

    public static void initialize(Context context){
        INSTANCE = new DoctorAppointmentDBHelper(context);
        PatientInfoList.initialize();
    }
    private DoctorAppointmentDBHelper(@Nullable Context context) {
        super(context, DoctorAppointmentContract.DATABASE_NAME, null, DoctorAppointmentContract.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        //insert sample dataa here
        insert(db,new PatientInfo("Kashyap",26,"Viral Fever","cold,cough","9999999999"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //update App widget here
    private void updateAppWidgetBroadcast(){
        Intent appWidgetIntent = new Intent(context, DoctorAppointmentWidgetProvider.class);
        appWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIdsArray = appWidgetManager.getAppWidgetIds(new ComponentName(context,DoctorAppointmentWidgetProvider.class));
        appWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,widgetIdsArray);
        context.sendBroadcast(appWidgetIntent);

    }

    private void insert(SQLiteDatabase database, PatientInfo patientInfo){
        ContentValues values = new ContentValues();
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry._ID,patientInfo.getId());
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_NAME,patientInfo.getName());
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_AGE,patientInfo.getAge());
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_DISEASE,patientInfo.getDisease());
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_SYMPTOMS,patientInfo.getSymptoms());
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_CREATED_TIME,patientInfo.getCreatedTime());
        values.put(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_PHONE_NUM,patientInfo.getPhoneNum());
        database.insert(DoctorAppointmentContract.DoctorAppointmentEntry.TABLE_NAME,null,values);
        updateAppWidgetBroadcast();
    }
    public void insert(PatientInfo patientInfo){
        SQLiteDatabase database = getWritableDatabase();
        insert(database,patientInfo);
    }

    public void delete(PatientInfo patientInfo){
        SQLiteDatabase database = getWritableDatabase();
        String where = DoctorAppointmentContract.DoctorAppointmentEntry._ID + "= ?";
        String[] whereArgs = {String.valueOf(patientInfo.getId())};
        database.delete(DoctorAppointmentContract.DoctorAppointmentEntry.TABLE_NAME,where,whereArgs);
        updateAppWidgetBroadcast();
    }

    public ArrayList<PatientInfo> getPatientInfo(){
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<PatientInfo> result = new ArrayList<>();
        String sortOrder = DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_CREATED_TIME+ " DESC";
        Cursor cursor = database.query(
                DoctorAppointmentContract.DoctorAppointmentEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder

        );
        while (cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_NAME));
            int age = cursor.getInt(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_AGE));
            String disease = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_DISEASE));
            String symptoms = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_SYMPTOMS));
            long createdTime = cursor.getLong(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_CREATED_TIME));
            String phoneNum = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_PHONE_NUM));
            result.add(new PatientInfo(id,name,age,disease,symptoms,createdTime,phoneNum));
        }
        cursor.close();
        return result;
    }
    public int getPatientCount(){
        return getPatientInfo().size();
    }
    public int getUniquePatient(){
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<PatientInfo> result = new ArrayList<>();
        Cursor cursor = database.query(true,
                DoctorAppointmentContract.DoctorAppointmentEntry.TABLE_NAME,
                new String[]{DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_PHONE_NUM},
                null,
        null,
                DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_PHONE_NUM,
                null,
                null,
                null);
        int count = cursor.getCount();
//        while (cursor.moveToNext()){
//            long id = cursor.getLong(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry._ID));
//            String name = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_NAME));
//            int age = cursor.getInt(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_AGE));
//            String disease = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_DISEASE));
//            String symptoms = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_SYMPTOMS));
//            long createdTime = cursor.getLong(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_CREATED_TIME));
//            String phoneNum = cursor.getString(cursor.getColumnIndex(DoctorAppointmentContract.DoctorAppointmentEntry.COLUMN_NAME_PHONE_NUM));
//            result.add(new PatientInfo(id,name,age,disease,symptoms,createdTime,phoneNum));
//        }
        cursor.close();
        return count;
    }


}
