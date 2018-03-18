package com.example.estelleyyy.clinic_in_a_box;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wenjiazhang on 2018-01-13.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patients.db";
    public static final String TABLE_NAME = "patients_table"; //patients_table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_AGE + " TEXT, " +
                COLUMN_PASSWORD + " TEXT " +
                ");" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

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

    //Add New Patient with Full Details
    public boolean insertPatient(Patient patient){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, patient.getFirstName());
        values.put(COLUMN_LASTNAME, patient.getLastName());
        values.put(COLUMN_AGE, patient.getAge());
        values.put(COLUMN_PASSWORD, patient.getPassword());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    //Search for LoginID given firstname
    public String searchID(String firstname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT firstname, _id FROM " + TABLE_NAME + " ORDER BY _id desc";
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
        String query = "SELECT _id, password from " + TABLE_NAME ;
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
        Cursor result = db.rawQuery("SELECT * from  " + TABLE_NAME, null);
        return result;
    }

    //Update Table Constrain
    public void updateTablePatient(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT, " +
                COLUMN_FIRSTNAME + " TEXT NOT NULL" +
                COLUMN_LASTNAME + " TEXT" +
                COLUMN_AGE + " TEXT" +
                COLUMN_PASSWORD + " TEXT" +
                ");" ;
        db.execSQL(query);
    }
}
