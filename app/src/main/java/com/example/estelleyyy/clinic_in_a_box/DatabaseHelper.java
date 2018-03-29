package com.example.estelleyyy.clinic_in_a_box;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDateTime;

/**
 * Created by wenjiazhang on 2018-01-13.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "patients.db";

    //Patients Information Table
    public static final String TABLE_NAME_PATIENT = "patients_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PASSWORD = "password";

    //Test Data Table
    public static final String TABLE_NAME_TEST = "tests_table";
    public static final String COLUMN_ID_2 = "TestID";
    public static final String COLUMN_TESTDATE = "TestDate";
    public static final String COLUMN_PATIENTID = "PatientID";
    public static final String COLUMN_TESTAGE = "TestAge";
    public static final String COLUMN_Q1 = "Q1";//questionnaire answers
    public static final String COLUMN_Q2 = "Q2";
    public static final String COLUMN_Q3 = "Q3";
    public static final String COLUMN_Q4 = "Q4";
    public static final String COLUMN_Q5 = "Q5";
    public static final String COLUMN_P1 = "P1";//physical tests results
    public static final String COLUMN_P2 = "P2";
    public static final String COLUMN_P3 = "P3";
    public static final String COLUMN_RISK = "RiskPercentage"; //in percentage

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME_PATIENT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_AGE + " TEXT, " +
                COLUMN_PASSWORD + " TEXT " +
                ");" ;
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_NAME_TEST + " (" +
                COLUMN_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PATIENTID + " INTEGER, " +
                COLUMN_TESTDATE + " DATE, " +
                COLUMN_TESTAGE + " FLOAT, " +
                COLUMN_Q1 + " INTEGER, " +
                COLUMN_Q2 + " INTEGER, " +
                COLUMN_Q3 + " INTEGER, " +
                COLUMN_Q4 + " INTEGER, " +
                COLUMN_Q5 + " INTEGER, " +
                COLUMN_P1 + " FLOAT, " +
                COLUMN_P2 + " FLOAT, " +
                COLUMN_P3 + " FLOAT, " +
                COLUMN_RISK + " FLOAT " +
                ");" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEST);
        onCreate(db);
    }

    /*
    //Add New Patient by First Name
    public boolean insertPatient(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, name);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }
*/

    //Add New Patient with Full Details
    public boolean insertPatient(Patient patient){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, patient.getFirstName());
        values.put(COLUMN_LASTNAME, patient.getLastName());
        values.put(COLUMN_AGE, patient.getAge());
        values.put(COLUMN_PASSWORD, patient.getPassword());

        long result = db.insert(TABLE_NAME_PATIENT, null, values);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    //Search for LoginID given firstname
    public String searchID(String firstname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT firstname, _id FROM " + TABLE_NAME_PATIENT + " ORDER BY _id desc";
        Cursor cursor = db.rawQuery(query, null);

        String a,b;
        b = "Not Found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(firstname)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return b;
    }

    //Search for valid password given userid
    public String searchPassword(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT _id, password from " + TABLE_NAME_PATIENT ;
        Cursor cursor = db.rawQuery(query, null);

        String a,b;
        b = "Not Found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(id)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }

        return b;
    }

    //Get all patients
    public Cursor getAllPatients(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * from  " + TABLE_NAME_PATIENT, null);
        return result;
    }

    public Cursor getAllTestData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * from  " + TABLE_NAME_TEST, null);
        return result;
    }


    //Insert Test Data
    public boolean insertTest(TestData testData){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PATIENTID,testData.getPatientID());
        values.put(COLUMN_Q1, testData.getQ1());
        values.put(COLUMN_Q2, testData.getQ2());
        values.put(COLUMN_Q3, testData.getQ3());
        values.put(COLUMN_Q4, testData.getQ4());
        values.put(COLUMN_Q5, testData.getQ5());
        values.put(COLUMN_P1, testData.getP1());
        values.put(COLUMN_P2, testData.getP2());
        values.put(COLUMN_P1, testData.getP3());
        //TO DO: ADD DATE TIME

        long result = db.insert(TABLE_NAME_TEST, null, values);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }
}
