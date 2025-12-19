package com.example.elecbill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "elecbill.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE bills (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "month TEXT," +
                        "units INTEGER," +
                        "rebate INTEGER," +
                        "total REAL," +
                        "final REAL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS bills");
        onCreate(db);
    }

    // Insert bill
    public void insertRecord(String month, int units, int rebate,
                             double total, double finalCost) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("month", month);
        cv.put("units", units);
        cv.put("rebate", rebate);
        cv.put("total", total);
        cv.put("final", finalCost);

        db.insert("bills", null, cv);
    }


    // Select all bills
    public Cursor getAllBills() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM bills ORDER BY id DESC", null);
    }

    // Update bill
    public void updateBill(int id, String month,
                           int units, int rebate,
                           double total, double finalCost) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("month", month);
        cv.put("units", units);
        cv.put("rebate", rebate);
        cv.put("total", total);
        cv.put("final", finalCost);

        db.update("bills", cv, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Delete bill
    public void deleteBill(int id) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete("bills", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
