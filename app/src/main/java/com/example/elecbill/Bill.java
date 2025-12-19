package com.example.elecbill;

public class Bill {

    private int id;
    private String month;
    private int units;
    private int rebate;
    private double total;
    private double finalCost;

    public Bill(int id, String month, int units,
                int rebate, double total, double finalCost) {

        this.id = id;
        this.month = month;
        this.units = units;
        this.rebate = rebate;
        this.total = total;
        this.finalCost = finalCost;
    }

    // getters
    public int getId() { return id; }
    public String getMonth() { return month; }
    public int getUnits() { return units; }
    public int getRebate() { return rebate; }
    public double getTotal() { return total; }
    public double getFinalCost() { return finalCost; }

    public void setValues(int units, int rebate, double total, double finalCost) {
        this.units = units;
        this.rebate = rebate;
        this.total = total;
        this.finalCost = finalCost;
    }

}
