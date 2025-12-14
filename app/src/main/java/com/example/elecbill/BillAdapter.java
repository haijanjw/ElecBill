package com.example.elecbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.bill_item, parent, false);
        }

        TextView txtMonth = convertView.findViewById(R.id.txtMonth);
        TextView txtFinal = convertView.findViewById(R.id.txtFinalCost);

        Bill bill = billList.get(position);

        txtMonth.setText(bill.getMonth());
        txtFinal.setText(
                "Final Cost: RM " + String.format("%.2f", bill.getFinalCost())
        );

        return convertView;
    }
}
