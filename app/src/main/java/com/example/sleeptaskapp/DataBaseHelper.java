package com.example.sleeptaskapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Task.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Task_table";
    private static final String COLUMN_KEY = "name";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "TASK";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "ENTIRE";
    public static final String COL_5 = "DAY";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, TIME TEXT, ENTIRE TEXT, DAY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String task, String time, String endtime,String DAY) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,task);
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,endtime);
        contentValues.put(COL_5,DAY);
        long result = -1;
        try {
             result = db.insert(TABLE_NAME, null, contentValues);
        } catch (SQLException e) {
            Log.e("ERROR",e.toString());
        }
        db.close();
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String id,String task, String time, String endtime,String DAY) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,task);
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,endtime);
        contentValues.put(COL_5,DAY);
        int result = db.update(TABLE_NAME, contentValues,"ID =?", new String[]{id});
        if(result > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME,"ID=?",new String[]{id});
        return i;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
        return res;
    }
}
