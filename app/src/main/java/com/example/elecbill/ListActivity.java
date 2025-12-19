package com.example.elecbill;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Bill> billList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        billList = new ArrayList<>();

        loadBills();

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Bill selectedBill = billList.get(position);

            Intent intent = new Intent(ListActivity.this, DetailActivity.class);
            intent.putExtra("month", selectedBill.getMonth());
            intent.putExtra("units", selectedBill.getUnits());
            intent.putExtra("rebate", selectedBill.getRebate());
            intent.putExtra("total", selectedBill.getTotal());
            intent.putExtra("finalCost", selectedBill.getFinalCost());

            startActivity(intent);
        });


        listView.setOnItemClickListener((parent, view, position, id) -> {

            Bill bill = billList.get(position);

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("month", bill.getMonth());
            intent.putExtra("units", bill.getUnits());
            intent.putExtra("rebate", bill.getRebate());
            intent.putExtra("total", bill.getTotal());
            intent.putExtra("finalCost", bill.getFinalCost());

            startActivity(intent);
        });
    }

    private void loadBills() {

        Cursor cursor = dbHelper.getAllBills();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No bills found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
            int units = cursor.getInt(cursor.getColumnIndexOrThrow("units"));
            int rebate = cursor.getInt(cursor.getColumnIndexOrThrow("rebate"));
            double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
            double finalCost = cursor.getDouble(cursor.getColumnIndexOrThrow("final"));

            billList.add(new Bill(id, month, units, rebate, total, finalCost));
        }

        cursor.close();

        listView.setAdapter(new BillAdapter(this, billList));
    }


}
