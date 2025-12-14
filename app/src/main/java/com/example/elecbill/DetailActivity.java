package com.example.elecbill;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView txtMonth, txtUnits, txtTotal, txtRebate, txtFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Link UI
        txtMonth = findViewById(R.id.txtMonth);
        txtUnits = findViewById(R.id.txtUnits);
        txtTotal = findViewById(R.id.txtTotal);
        txtRebate = findViewById(R.id.txtRebate);
        txtFinal = findViewById(R.id.txtFinal);

        // Get data from intent
        String month = getIntent().getStringExtra("month");
        int units = getIntent().getIntExtra("units", 0);
        double totalCharges = getIntent().getDoubleExtra("totalCharges", 0);
        int rebate = getIntent().getIntExtra("rebate", 0);
        double finalCost = getIntent().getDoubleExtra("finalCost", 0);

        // Set values (NO string resources → NO errors)
        txtMonth.setText("Month: " + month);
        txtUnits.setText("Units Used: " + units);
        txtTotal.setText(String.format(Locale.getDefault(),
                "Total Charges: RM %.2f", totalCharges));
        txtRebate.setText("Rebate: " + rebate + "%");
        txtFinal.setText(String.format(Locale.getDefault(),
                "Final Cost: RM %.2f", finalCost));
    }
}
