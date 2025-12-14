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

        if (editUnits.getText().toString().isEmpty() ||
                editRebate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(editUnits.getText().toString());
        int rebate = Integer.parseInt(editRebate.getText().toString());
        String month = spinnerMonth.getSelectedItem().toString();

        double total = calculateTotalCharges(units);
        double finalCost = total - (total * rebate / 100.0);

        txtTotal.setText(String.format(Locale.getDefault(),
                "Total Charges: RM %.2f", total));
        txtFinal.setText(String.format(Locale.getDefault(),
                "Final Cost: RM %.2f", finalCost));

        dbHelper.insertRecord(month, units, total, rebate, finalCost);
    }

    // ✅ TNB BLOCK CALCULATION (CORRECT)
    private double calculateTotalCharges(int units) {
        if (units <= 200) {
            return units * 0.218;
        } else if (units <= 300) {
            return (200 * 0.218) + (units - 200) * 0.334;
        } else if (units <= 600) {
            return (200 * 0.218) + (100 * 0.334) + (units - 300) * 0.516;
        } else {
            return (200 * 0.218) + (100 * 0.334) +
                    (300 * 0.516) + (units - 600) * 0.546;
        }
    }
}
