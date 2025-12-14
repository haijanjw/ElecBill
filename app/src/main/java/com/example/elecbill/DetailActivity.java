package com.example.elecbill;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView txtMonth, txtUnits, txtTotal, txtRebate, txtFinal, txtDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtMonth = findViewById(R.id.txtMonth);
        txtUnits = findViewById(R.id.txtUnits);
        txtTotal = findViewById(R.id.txtTotal);
        txtRebate = findViewById(R.id.txtRebate);
        txtFinal = findViewById(R.id.txtFinal);
        txtDetail = findViewById(R.id.txtDetail);

        // Get data from intent
        String month = getIntent().getStringExtra("month");
        int units = getIntent().getIntExtra("units", 0);
        double total = getIntent().getDoubleExtra("total", 0);
        int rebate = getIntent().getIntExtra("rebate", 0);
        double finalCost = getIntent().getDoubleExtra("final", 0);

        txtMonth.setText("Month: " + month);
        txtUnits.setText("Units Used: " + units + " kWh");
        txtTotal.setText("Total Charges: RM " + String.format("%.2f", total));
        txtRebate.setText("Rebate: " + rebate + "%");
        txtFinal.setText("Final Cost: RM " + String.format("%.2f", finalCost));

    }
}
