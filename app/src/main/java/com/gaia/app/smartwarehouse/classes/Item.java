package com.gaia.app.smartwarehouse.classes;

/**
 * Created by anant on 13/06/16.
 */

public class Item {
    private String iname, cname, unit, weight, quant;
    private int size;

    public Item(String iname, String cname, String unit, String weight, String quant) {
        this.iname = iname;
        this.cname = cname;
        this.unit = unit;
        this.weight = weight;
        this.quant = quant;
    }

    public String getIname() {

        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
