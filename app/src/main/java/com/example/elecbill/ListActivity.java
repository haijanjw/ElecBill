package com.example.elecbill;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Bill> list = new ArrayList<>();
    DBHelper db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_list);

        db = new DBHelper(this);
        ListView lv = findViewById(R.id.listView);

        Cursor c = db.getAllBills();
        while (c.moveToNext()) {
            list.add(new Bill(
                    c.getString(1),
                    c.getDouble(5)
            ));
        }

        lv.setAdapter(new BillAdapter(this, list));
    }
}
