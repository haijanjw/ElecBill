package com.example.elecbill;

public class Bill {

    private String month;
    private double finalCost;

    // Constructor
    public Bill(String month, double finalCost) {
        this.month = month;
        this.finalCost = finalCost;
    }

    // ✅ GETTERS (THIS WAS MISSING)
    public String getMonth() {
        return month;
    }

    public double getFinalCost() {
        return finalCost;
    }
}
