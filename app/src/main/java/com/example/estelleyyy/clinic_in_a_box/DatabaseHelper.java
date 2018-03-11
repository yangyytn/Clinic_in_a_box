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
    public static final String TABLE_NAME = "patients_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PATIENTNAME = "_patientname";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PATIENTNAME + " TEXT " +
                ");" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    //Add new patient
    public boolean insertPatient(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENTNAME, name);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Add new patient
    public Cursor getAllPatients(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

    //Print out the database as a string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + "WHERE 1";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("patientname"))!=null){
                dbString += c.getString(c.getColumnIndex("patientname"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }
}
