package com.example.elecbill;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "elecbill.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE bills (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "month TEXT," +
                        "units INTEGER," +
                        "total REAL," +
                        "rebate INTEGER," +
                        "final REAL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bills");
        onCreate(db);
    }

    public void insertRecord(String month, int units, double total,
                             int rebate, double finalCost) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("month", month);
        cv.put("units", units);
        cv.put("total", total);
        cv.put("rebate", rebate);
        cv.put("final", finalCost);

        db.insert("bills", null, cv);
    }

    public Cursor getAllBills() {
        return getReadableDatabase()
                .rawQuery("SELECT * FROM bills ORDER BY id DESC", null);
    }
}
