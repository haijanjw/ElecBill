package com.example.elecbill;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView txtMonth, txtUnits, txtTotal, txtRebate, txtFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtMonth = findViewById(R.id.txtMonth);
        txtUnits = findViewById(R.id.txtUnits);
        txtTotal = findViewById(R.id.txtTotal);
        txtRebate = findViewById(R.id.txtRebate);
        txtFinal = findViewById(R.id.txtFinal);

        txtMonth.setText("Month: " + getIntent().getStringExtra("month"));
        txtUnits.setText("Units Used: " + getIntent().getIntExtra("units", 0));
        txtTotal.setText("Total Charges: RM " +
                String.format("%.2f", getIntent().getDoubleExtra("total", 0)));
        txtRebate.setText("Rebate: " + getIntent().getIntExtra("rebate", 0) + "%");
        txtFinal.setText("Final Cost: RM " +
                String.format("%.2f", getIntent().getDoubleExtra("finalCost", 0)));
    }
}
