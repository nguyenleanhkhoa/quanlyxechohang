package model;

import java.io.Serializable;

/**
 * Created by kyosh on 5/2/2017.
 */

public class thongke implements Serializable {
    private String date;
    private String custome_count;

    public thongke() {
    }

    public thongke(String date, String custome_count) {
        this.date = date;
        this.custome_count = custome_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustome_count() {
        return custome_count;
    }

    public void setCustome_count(String custome_count) {
        this.custome_count = custome_count;
    }
}
