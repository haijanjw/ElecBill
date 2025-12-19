package com.example.elecbill;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMonth;
    EditText editUnits, editRebate;
    TextView txtTotal, txtFinal;
    DBHelper dbHelper;

    // store values so UI doesn't reset
    double totalSaved = 0;
    double finalSaved = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        editUnits = findViewById(R.id.editUnits);
        editRebate = findViewById(R.id.editRebate);
        txtTotal = findViewById(R.id.txtTotal);
        txtFinal = findViewById(R.id.txtFinal);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnAbout = findViewById(R.id.btnAbout);

        dbHelper = new DBHelper(this);

        String[] months = {
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"
        };
        spinnerMonth.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months)
        );

        btnCalculate.setOnClickListener(v -> calculateAndSave());

        btnHistory.setOnClickListener(v ->
                startActivity(new Intent(this, ListActivity.class)));

        btnAbout.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));
    }


    private void calculateAndSave() {

        String unitsStr = editUnits.getText().toString().trim();
        String rebateStr = editRebate.getText().toString().trim();

        boolean hasError = false;

        if (unitsStr.isEmpty()) {
            editUnits.setError("Enter electricity usage (kWh)");
            hasError = true;
        }

        if (rebateStr.isEmpty()) {
            editRebate.setError("Enter rebate percentage");
            hasError = true;
        }

        if (hasError) {
            Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            return;  // stop here
        }

        int units = Integer.parseInt(unitsStr);
        int rebate = Integer.parseInt(rebateStr);
        String month = spinnerMonth.getSelectedItem().toString();

        double total = calculateTotalCharges(units);
        double finalCost = total - (total * rebate / 100.0);

        totalSaved = total;
        finalSaved = finalCost;

        txtTotal.setText(String.format(Locale.getDefault(),
                "Total Charges: RM %.2f", totalSaved));
        txtFinal.setText(String.format(Locale.getDefault(),
                "Final Cost: RM %.2f", finalSaved));

        dbHelper.insertRecord(month, units, rebate, totalSaved, finalSaved);

        Toast.makeText(this, "Bill saved successfully!", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onResume() {
        super.onResume();

        if (totalSaved > 0) {
            txtTotal.setText(String.format("Total Charges: RM %.2f", totalSaved));
            txtFinal.setText(String.format("Final Cost: RM %.2f", finalSaved));
        }
    }


    // TNB BLOCK CALCULATION
    private double calculateTotalCharges(int units) {

        if (units <= 200) {
            return units * 0.218;

        } else if (units <= 300) {
            return (200 * 0.218) + (units - 200) * 0.334;

        } else if (units <= 600) {
            return (200 * 0.218) + (100 * 0.334) + (units - 300) * 0.516;

        } else {
            return (200 * 0.218) + (100 * 0.334)
                    + (300 * 0.516) + (units - 600) * 0.546;
        }
    }
}
