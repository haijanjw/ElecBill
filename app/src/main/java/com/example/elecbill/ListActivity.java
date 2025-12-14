package com.example.elecbill;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    ArrayList<Bill> billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        billList = new ArrayList<>();

        loadBills();

        // **FIX:** Moved the setOnItemClickListener inside the onCreate method
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill selectedBill = billList.get(position);

                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("month", selectedBill.getMonth());
                intent.putExtra("finalCost", selectedBill.getFinalCost());

                startActivity(intent);
            }
        });
    }

    private void loadBills() {
        Cursor cursor = dbHelper.getAllBills();

        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "No bills found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            String month = cursor.getString(
                    cursor.getColumnIndexOrThrow("month")
            );
            double finalCost = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("final")
            );

            billList.add(new Bill(month, finalCost));
        }

        cursor.close();

        BillAdapter adapter = new BillAdapter(this, billList);
        listView.setAdapter(adapter);
    }
}
