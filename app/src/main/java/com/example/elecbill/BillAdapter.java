package com.example.elecbill;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class BillAdapter extends ArrayAdapter<Bill> {

    public BillAdapter(Context c, ArrayList<Bill> list) {
        super(c, 0, list);
    }

    @Override
    public View getView(int pos, View v, ViewGroup parent) {
        if (v == null)
            v = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);

        Bill b = getItem(pos);
        ((TextView)v.findViewById(android.R.id.text1)).setText(b.month);
        ((TextView)v.findViewById(android.R.id.text2))
                .setText(String.format("Final Cost: RM %.2f", b.finalCost));

        return v;
    }
}
