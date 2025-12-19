package com.example.elecbill;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BillAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Bill> billList;

    public BillAdapter(Context context, ArrayList<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // billing calculation for update/edit
    private double calcTotal(int units) {
        if (units <= 200) return units * 0.218;
        else if (units <= 300)
            return 200 * 0.218 + (units - 200) * 0.334;
        else if (units <= 600)
            return 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        else
            return 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.bill_item, parent, false);
        }

        TextView txtMonth = convertView.findViewById(R.id.txtMonth);
        TextView txtFinal = convertView.findViewById(R.id.txtFinalCost);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        Bill bill = billList.get(position);

        txtMonth.setText(bill.getMonth());
        txtFinal.setText("RM " + String.format("%.2f", bill.getFinalCost()));

        // DELETE BILL
        btnDelete.setOnClickListener(v -> {
            DBHelper db = new DBHelper(context);
            db.deleteBill(bill.getId());

            billList.remove(position);
            notifyDataSetChanged();

            Toast.makeText(context, "Bill deleted", Toast.LENGTH_SHORT).show();
        });

        // EDIT BILL
        btnEdit.setOnClickListener(v -> {

            View dialogView = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_edit_bill, null);

            EditText editUnits = dialogView.findViewById(R.id.editUnits);
            EditText editRebate = dialogView.findViewById(R.id.editRebate);

            editUnits.setText(String.valueOf(bill.getUnits()));
            editRebate.setText(String.valueOf(bill.getRebate()));

            new AlertDialog.Builder(context)
                    .setTitle("Edit Bill")
                    .setView(dialogView)
                    .setPositiveButton("Save", (dialog, which) -> {

                        int newUnits = Integer.parseInt(editUnits.getText().toString());
                        int newRebate = Integer.parseInt(editRebate.getText().toString());

                        double total = calcTotal(newUnits);
                        double finalCost =
                                total - (total * newRebate / 100.0);

                        DBHelper db = new DBHelper(context);
                        db.updateBill(
                                bill.getId(),
                                bill.getMonth(),
                                newUnits,
                                newRebate,
                                total,
                                finalCost
                        );

                        // update in array list
                        bill.setValues(newUnits, newRebate, total, finalCost);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Bill updated", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        return convertView;
    }
}
