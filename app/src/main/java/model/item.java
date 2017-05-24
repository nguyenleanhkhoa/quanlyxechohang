package model;

import java.io.Serializable;

/**p
 * Created by kyosh on 4/19/2017.
 */

public class item implements Serializable {
    public int ID;
    public byte[] avata;
    public String firstname;
    public String lastname;
    public String address;
    public String phone;
    public long billnum;
    public String date;
    public String time;
    public String originpoint;
    public String finalpoint;
    public String type;
    public String note;
    public String money;

    public item() {
    }

    public item(int ID, byte[] avata, String firstname, String lastname, String address, String phone, long billnum, String date, String time, String originpoint, String finalpoint, String type, String note, String money) {
        this.ID = ID;
        this.avata = avata;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.billnum = billnum;
        this.date = date;
        this.time = time;
        this.originpoint = originpoint;
        this.finalpoint = finalpoint;
        this.type = type;
        this.note = note;
        this.money = money;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public byte[] getAvata() {
        return avata;
    }

    public void setAvata(byte[] avata) {
        this.avata = avata;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getBillnum() {
        return billnum;
    }

    public void setBillnum(long billnum) {
        this.billnum = billnum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOriginpoint() {
        return originpoint;
    }

    public void setOriginpoint(String originpoint) {
        this.originpoint = originpoint;
    }

    public String getFinalpoint() {
        return finalpoint;
    }

    public void setFinalpoint(String finalpoint) {
        this.finalpoint = finalpoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
