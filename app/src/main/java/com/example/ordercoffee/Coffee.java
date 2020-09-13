package com.example.ordercoffee;

import java.io.Serializable;

class Coffee implements Serializable {
    private int imgId;
    private String name;
    private String subtext1;
    private String subtext2;
    private int priceof;
    private int no_coffee=0;
    private boolean exp;
    static int total_no_coffee=0;
    static int total_cost=0;

    Coffee(int imgId, String name, String subtext1, String subtext2, int priceof) {
        this.imgId = imgId;
        this.name = name;
        this.subtext1 = subtext1;
        this.subtext2 = subtext2;
        this.priceof = priceof;
    }

    int getImgId() {
        return imgId;
    }

    String getName() {
        return name;
    }

    String getSubtext1() {
        return subtext1;
    }

    String getSubtext2() {
        return subtext2;
    }

    int getPriceof() {
        return priceof;
    }

    int getNo_coffee() {
        return no_coffee;
    }

    void setNo_coffee(int no_coffee) {
        this.no_coffee = no_coffee;
    }

    //Getter of exp
    boolean isExpanded() {
        return exp;
    }

    void setExpansion(boolean exp) {
        this.exp = exp;
    }
}